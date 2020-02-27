package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * this a network fot torch gru
 * 
 * @author xuen
 *
 * @param <T>
 */
public class TGRU<T extends Matrix<T>> extends NeuralNetwork {

	private final T wIh;
	private final T wHh;
	private final T bIh;
	private final T bHh;
	private final MatrixFunc<T> iactive;
	private final MatrixFunc<T> oactive;

	public TGRU(T wIh, T wHh, T bIh, T bHh, MatrixFunc<T> iactive, MatrixFunc<T> oactive) {
		if (wIh == null || wHh == null || iactive == null || oactive == null)
			throw new NullPointerException("all args must be not null");
		this.wIh = wIh;
		this.wHh = wHh;
		this.bIh = bIh;
		this.bHh = bHh;
		this.iactive = iactive;
		this.oactive = oactive;
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T zeroState(int batchSize) {
		if (this.wHh instanceof FloatMatrix)
			return (T) new FloatMatrix(this.wHh.rows, batchSize);
		if (this.wHh instanceof DoubleMatrix)
			return (T) new DoubleMatrix(this.wHh.rows, batchSize);
		throw new RuntimeException("not support num type" + this.wHh.getClass().getName());
	}

	private T linear(T args, T weights, T biases) {
		T x = args.mmul(weights, false, false, 1.0);
		if (biases != null)
			x = x.addRowVector(biases, x);
		return x;
	}

	public T forward(T state, T input) {
		T gi = linear(input, wIh, bIh);
		T gh = linear(state, wHh, bHh);
		int len = gi.columns / 3;
		T i_r = gi.getRange(0, gi.rows, 0, len);
		T i_i = gi.getRange(0, gi.rows, len, len * 2);
		T i_n = gi.getRange(0, gi.rows, len * 2, len * 3);
		len = gh.columns / 3;
		T h_r = gh.getRange(0, gh.rows, 0, len);
		T h_i = gh.getRange(0, gh.rows, len, len * 2);
		T h_n = gh.getRange(0, gh.rows, len * 2, len * 3);

		T resetgate = iactive.call(i_r.add(h_r, i_r));
		T inputgate = iactive.call(i_i.add(h_i, i_i));
		T newgate = oactive.call(i_n.add(resetgate.mul(h_n, resetgate), i_n));
		T hy = newgate.add(inputgate.mul(state.sub(newgate, state), inputgate), newgate);
		return hy;
	}

	public int GetStateSize() {
		return this.wHh.rows;
	}

	/**
	 * input size is [time_step,1,vec_size]
	 * 
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T[] runRnn(T[] input) {
		T[] outputs = (T[]) Array.newInstance(this.wHh.getClass(), input.length);
		T state = this.zeroState(1);
		for (int i = 0; i < input.length; i++) {
			state = forward(input[i], state);// update state
			outputs[i] = state.dup();// copy state data
		}
		return outputs;
	}

	/**
	 * reverse run rnn cell input size is [time_step,1,vec_size]
	 * 
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T[] reverseRunRnn(T[] input) {
		T[] outputs = (T[]) Array.newInstance(this.wHh.getClass(), input.length);
		T state = this.zeroState(1);
		for (int i = input.length - 1; i >= 0; i--) {
			state = forward(input[i], state);// update state
			outputs[i] = state.dup();// copy state data
		}
		return outputs;
	}

	@Override
	public void save(DataOutputStream out) throws IOException {
		saveM(out, wIh);
		saveM(out, wHh);
		saveM(out, bIh);
		saveM(out, bHh);

		saveAF(out, iactive);
		saveAF(out, oactive);

	}
	
	
	/**
	 * load data
	 * 
	 * @param <T>
	 * @param type
	 * @param in
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static TGRU load(DataInputStream in) throws IOException {
		Matrix wIh = loadM(in);
		Matrix wHh = loadM(in);
		Matrix bIh = loadM(in);
		Matrix bHh = loadM(in);
		MatrixFunc iactive = loadAF(in);
		MatrixFunc oactive = loadAF(in);
		return new TGRU(wIh, wHh, bIh, bHh, iactive, oactive);
	}

}

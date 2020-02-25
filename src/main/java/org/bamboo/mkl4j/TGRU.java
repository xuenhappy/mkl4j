package org.bamboo.mkl4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;

/**
 * this a network fot torch gru
 * 
 * @author xuen
 *
 * @param <T>
 */
public class TGRU<T extends Matrix<T>> implements NeuralNetwork<T> {
	private T wIh;
	private T wHh;
	private T bIh;
	private T bHh;

	private MatrixFunc<T> iactive;
	private MatrixFunc<T> oactive;

	/**
	 * 
	 * @return
	 */
	public T zeroState(int batchSize) {
		return this.wHh.zeros(this.wHh.rows, batchSize);
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

	public TGRU(T wIh, T wHh, T bIh, T bHh, MatrixFunc<T> iactive, MatrixFunc<T> oactive) {
		this.wHh = wHh.transpose();
		this.wIh = wIh.transpose();
		this.bHh = bHh;
		this.bIh = bIh;

		this.iactive = iactive;
		this.oactive = oactive;
	}

	public TGRU() {
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
	public void load(InputStream in) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(OutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

}

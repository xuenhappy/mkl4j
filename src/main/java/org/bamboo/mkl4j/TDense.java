package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * this is a dense net work for torch
 * 
 * @author xuen
 *
 * @param <T>
 */
public class TDense<T extends Matrix<T>> extends NeuralNetwork {

	

	private final MatrixFunc<T> activation;
	private final T weight;
	private final T blas;

	public TDense(T weight, T blas, MatrixFunc<T> activation) {
		if (weight == null)
			throw new NullPointerException("weight must be not null");
		this.activation = activation;
		this.weight = weight.transpose();
		this.blas = blas;
	}

	public T forward(T input) {
		T val = input.mmul(weight, false, false, 1.0);
		if (blas != null)
			val = val.addRowVector(blas, val);
		if (activation != null)
			val = activation.call(val);
		return val;
	}

	@Override
	public void save(DataOutputStream out) throws IOException {
		saveM(out, weight);
		saveM(out, blas);
		saveAF(out, activation);
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
	public static TDense load(DataInputStream in) throws IOException {
		Matrix w = loadM(in);
		Matrix b = loadM(in);
		MatrixFunc f = loadAF(in);
		return new TDense(w, b, f);
	}

}

package org.bamboo.mkl4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * this is a dense net work for torch
 * 
 * @author xuen
 *
 * @param <T>
 */
public class TDense<T extends Matrix<T>> implements NeuralNetwork<T> {
	private MatrixFunc<T> activation;
	private T weight;
	private T blas;

	public TDense() {
	}

	public TDense(T weight, T blas, MatrixFunc<T> activation) {
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
	public void load(InputStream in) throws IOException {

	}

	@Override
	public void save(OutputStream out) throws IOException {

	}

}

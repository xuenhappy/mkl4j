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
public class TDense<T extends Matrix<T>> implements NeuralNetwork{
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

	@SuppressWarnings("unchecked")
	@Override
	public void load(DataInputStream in) throws IOException {
		this.weight = this.weight.load(in);
		if (in.readChar() == 'c') {
			this.blas = this.blas.load(in);
		} else {
			this.blas = null;
		}
		if (in.readChar() == 'k') {
			this.activation = Activation.load(in, this.blas.getClass());
		} else {
			this.activation = null;
		}
	}

	@Override
	public void save(DataOutputStream out) throws IOException {
		this.weight.save(out);
		if (this.blas != null) {
			out.writeChar('c');
			this.blas.save(out);
		} else {
			out.writeChar('n');
		}
		if (this.activation != null) {
			out.writeChar('k');
			Activation.save(out, activation);
		} else {
			out.writeChar('l');
		}

	}

}

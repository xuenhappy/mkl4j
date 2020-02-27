package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * the nural usually active function
 * 
 * @author xuen
 *
 */
public final class Activation {

	/**
	 * all funcs
	 */
	private static final Map<String, MatrixFunc<?>> funcs = new HashMap<String, MatrixFunc<?>>();

	/**
	 * this is a float tanh function
	 */
	public static final MatrixFunc<FloatMatrix> F_Tanh = new MatrixFunc<FloatMatrix>() {
		@Override
		public FloatMatrix call(FloatMatrix input) {
			return input.tanh(input);
		}

		@Override
		public String name() {
			return "F_Tanh";
		}
	};

	/**
	 * this is a float exp function
	 */
	public static final MatrixFunc<FloatMatrix> F_Exp = new MatrixFunc<FloatMatrix>() {
		@Override
		public FloatMatrix call(FloatMatrix input) {
			return input.exp(input);
		}

		@Override
		public String name() {
			return "F_Exp";
		}
	};

	/**
	 * this is a float sigmoid function
	 */
	public static final MatrixFunc<FloatMatrix> F_Sigmoid = new MatrixFunc<FloatMatrix>() {
		@Override
		public FloatMatrix call(FloatMatrix input) {
			return input.sigmoid(input);
		}

		@Override
		public String name() {
			return "F_Sigmoid";
		}
	};

	/**
	 * get the activation by name
	 * 
	 * @param <T>
	 * @param cls
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static final <T extends Matrix<T>> MatrixFunc<T> getAcivation(Class<T> cls, String name) {
		synchronized (funcs) {
			if (funcs.isEmpty()) {

			}
		}
		return (MatrixFunc<T>) funcs.get(name);
	}

	public static <T extends Matrix<T>> MatrixFunc<T> load(DataInputStream in, Class<T> cls) throws IOException {
		String name = in.readUTF();
		return getAcivation(cls, name);

	}

	public static <T extends Matrix<T>> void save(DataOutputStream out, MatrixFunc<T> func) throws IOException {
		out.writeUTF(func.name());
	}

}

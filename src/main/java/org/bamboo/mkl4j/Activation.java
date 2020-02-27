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

	@SuppressWarnings("rawtypes")
	private static final Map<String, MatrixFunc> funcs = new HashMap<String, MatrixFunc>();

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
	 * this is a float sigmoid function
	 */
	public static final MatrixFunc<FloatMatrix> F_RELU = new MatrixFunc<FloatMatrix>() {
		@Override
		public FloatMatrix call(FloatMatrix input) {
			return input.relu(input);
		}

		@Override
		public String name() {
			return "F_RELU";
		}
	};

	@SuppressWarnings("rawtypes")
	public static MatrixFunc load(DataInputStream in) throws IOException {
		String name = in.readUTF();
		synchronized (funcs) {
			if (funcs.isEmpty()) {
				funcs.put(F_Tanh.name(), F_Tanh);
				funcs.put(F_Sigmoid.name(), F_Sigmoid);
				funcs.put(F_Exp.name(), F_Exp);
				funcs.put(F_RELU.name(), F_RELU);
			}
		}
		return funcs.get(name);
	}

	@SuppressWarnings("rawtypes")
	public static void save(DataOutputStream out, MatrixFunc func) throws IOException {
		out.writeUTF(func.name());
	}

}

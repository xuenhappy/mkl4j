package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * this is the net
 * 
 * @author xuen
 *
 */
@SuppressWarnings("rawtypes")
public abstract class NeuralNetwork {

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

	private static final Map<String, MatrixFunc> funcs = new HashMap<String, MatrixFunc>();

	/**
	 * save the network to stream
	 * 
	 * @param out
	 * @throws IOException
	 */
	public abstract void save(DataOutputStream out) throws IOException;

	/**
	 * load activation func support null
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static MatrixFunc loadAF(DataInputStream in) throws IOException {
		String name = in.readUTF();
		if ("NULL".endsWith(name))
			return null;
		synchronized (funcs) {
			if (funcs.isEmpty()) {
				funcs.put(F_Tanh.name(), F_Tanh);
				funcs.put(F_Sigmoid.name(), F_Sigmoid);
				funcs.put(F_Exp.name(), F_Exp);
				funcs.put(F_RELU.name(), F_RELU);
			}
		}
		MatrixFunc c = funcs.get(name);
		if (c == null)
			throw new IOException("not support activation function[+" + name + "" + "]");
		return c;
	}

	/**
	 * save activation function support null
	 * 
	 * @param out
	 * @param func
	 * @throws IOException
	 */
	public static void saveAF(DataOutputStream out, MatrixFunc func) throws IOException {
		if (func == null)
			out.writeUTF("NULL");
		out.writeUTF(func.name());
	}

	/**
	 * load matrix support null
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static Matrix loadM(DataInputStream in) throws IOException {
		char c = in.readChar();
		if (c == 'N') {
			return null;
		}
		if (c == 'd') {
			return DoubleMatrix.load(in);
		} else if (c == 'f') {
			return FloatMatrix.load(in);
		} else {
			throw new IOException("not support read type" + c);
		}
	}

	/**
	 * save matrix support null
	 * 
	 * @param out
	 * @param func
	 * @throws IOException
	 */
	public static void saveM(DataOutputStream out, Matrix func) throws IOException {
		if (func == null) {
			out.writeChar('N');
		} else if (func instanceof DoubleMatrix) {
			out.writeChar('d');
			func.save(out);
		} else if (func instanceof FloatMatrix) {
			out.writeChar('f');
			func.save(out);
		} else {
			throw new IOException("not support write type:" + func.getClass().getName());
		}
	}

}

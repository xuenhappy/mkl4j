package org.bamboo.mkl4j;

/**
 * the nural usually active function
 * 
 * @author xuen
 *
 */
public interface Activation {
	/**
	 * this is a float tanh function
	 */
	public static MatrixFunc<FloatMatrix> F_Tanh = new MatrixFunc<FloatMatrix>() {
		@Override
		public FloatMatrix call(FloatMatrix input) {
			return input.tanh(input);
		}
	};

	/**
	 * this is a float exp function
	 */
	public static MatrixFunc<FloatMatrix> F_Exp = new MatrixFunc<FloatMatrix>() {
		@Override
		public FloatMatrix call(FloatMatrix input) {
			return input.exp(input);
		}
	};

	/**
	 * this is a float sigmoid function
	 */
	public static MatrixFunc<FloatMatrix> F_Sigmoid = new MatrixFunc<FloatMatrix>() {
		@Override
		public FloatMatrix call(FloatMatrix input) {
			return input.exp(input.div(2, input)).add(1, input).div(2, input);
		}
	};

}

package org.bamboo.mkl4j;

import java.util.Arrays;

public class TestFloatMt {
	public static void main(String[] args) {
		float[][] data = new float[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };
		FloatMatrix m = new FloatMatrix(data);
		System.out.println(m);
		System.out.println("------------------------------");
		System.out.println(m.transpose());
		System.out.println(m);
		System.out.println("------------------------------");
		System.out.println(m.getCol(2));
		System.out.println(Arrays.toString(m.nrm2('r')));
		System.out.println(Arrays.toString(m.nrm2('c')));
		System.out.println("---------------------------------");
		System.out.println(Arrays.toString(m.argMax('r')));
		System.out.println(Arrays.toString(m.argMax('c')));
		System.out.println(Arrays.toString(m.argMin('r')));
		System.out.println(Arrays.toString(m.argMin('c')));
		System.out.println("----------------------------------");
		System.out.println(m.getRange(0, 2, 1, 3));
		System.out.println(m.getRow(1));
		System.out.println(m.getCol(2));
		System.out.println("----------------------------------");
		System.out.println(m.add(2.0f, m.zeroLikeThis()));
		System.out.println(m);
		System.out.println(
				m.addRowVector(new FloatMatrix(1, 4, new float[] { 2.0f, 2.3f, 1.2f, 6.5f }), m.zeroLikeThis()));
		System.out.println(m);
		System.out.println("+++++++++++++++++++++++++++++++++++++++");

		float[][] data1 = new float[][] { { 3, 5, 2, 4 }, { 8, 6, 10, 12 } };
		FloatMatrix m1 = new FloatMatrix(data1);
		System.out.println(m.add(m1, m1));
		System.out.println(m1);
		System.out.println("-----------------------------------");
		System.out.println(m);
		System.out.println(m.tanh(m.zeroLikeThis()));
		System.out.println(m);
		System.out.println("++++++++++++++++++");
		float[][] c = new float[][] { { 2, 3 }, { 5, 6 }, { 6, 3 }, { 8, 9 } };
		FloatMatrix n = new FloatMatrix(c);
		System.out.println(n);
		System.out.println(FloatMatrix.mmul(m, n, false, false, 1.0f));
		System.out.println("++++++++++++++++++++++++++++++++++");
		float[][] c1 = new float[][] { { 2 ,  5 } ,{ 6 ,  8 }};
		FloatMatrix n1 = new FloatMatrix(c1);
		System.out.println(m);
		System.out.println(n1);
		System.out.println(FloatMatrix.mmul(m, n1, true, false, 1.0f));

	}

}

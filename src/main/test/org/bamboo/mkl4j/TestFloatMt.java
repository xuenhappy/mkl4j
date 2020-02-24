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

	}

}

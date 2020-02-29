package org.bamboo.mkl4j;

import java.util.Arrays;

public class TestFloatMt {
	public static void main(String[] args) {
		float[][] data = new float[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };
		FloatMatrix m = new FloatMatrix(data);
		System.out.println("----------------basic op--------------");
		System.out.println("ori matrix:" + m);
		System.out.println("matrix transpose:" + m.transpose());
		System.out.println("matrix get rc:" + m.getRange(0, 2, 1, 3));
		System.out.println("matrix get row:" + m.getRow(1));
		System.out.println("mtraix get col:" + m.getCol(2));
		System.out.println("-----------------along dim op-------------");
		System.out.println("ori matrix:" + m);
		System.out.println("matix along row softmax:" + m.softmax('r', m.numFill(0, true)));
		System.out.println("matix along col softmax:" + m.softmax('c', m.numFill(0, true)));
		System.out.println("ori matrix:" + m);
		System.out.println("matix along row sum:" + m.sum('r'));
		System.out.println("matix along col sum:" + m.sum('c'));
		System.out.println("matix along row nrm2:" + m.nrm2('r'));
		System.out.println("matix along col nrm2:" + m.nrm2('c'));
		System.out.println("matix along row max:" + Arrays.toString(m.argMax('r')));
		System.out.println("matix along col max:" + Arrays.toString(m.argMax('c')));
		System.out.println("matix along row min:" + Arrays.toString(m.argMin('r')));
		System.out.println("matix along col min:" + Arrays.toString(m.argMin('c')));

		System.out.println("----------------mm op-----------------");
		System.out.println("add scalr:" + m.add(2.0f, m.numFill(0, true)));
		System.out.println("ori matrix:" + m);
		FloatMatrix b = new FloatMatrix(1, 4, new float[] { 2.0f, 2.3f, 1.2f, 6.5f });
		System.out.println("add row vector:" + m.addRowVector(b, m.numFill(0, true)));
		FloatMatrix m1 = new FloatMatrix(new float[][] { { 3, 5, 2, 4 }, { 8, 6, 10, 12 } });
		System.out.println("add other matrix:" + m1);
		System.out.println("result:" + m.add(m1, m1));
		FloatMatrix n = new FloatMatrix(new float[][] { { 2, 3 }, { 5, 6 }, { 6, 3 }, { 8, 9 } });
		System.out.println("matrix dot matrix:" + n);
		System.out.println("result:" + m.mmul(n, false, false, 1.0f));
		FloatMatrix n1 = new FloatMatrix(new float[][] { { 2, 5 }, { 6, 8 } });
		System.out.println("ori matrix:" + m);
		System.out.println("dot matrix:" + n1);
		System.out.println("trans A dot:" + m.mmul(n1, true, false, 1.0f));
		System.out.println("-------------------math op----------------");
		FloatMatrix t = new FloatMatrix(new float[][] { { -3, 5, 2, 4 }, { 8, 6, -10, 12 } });
		System.out.println("ori matrix:" + t);
		System.out.println("tanh result:" + t.tanh(t.numFill(0, true)));
		System.out.println("sigmoid result:" + t.sigmoid(m.numFill(0, true)));
		System.out.println("relu result:"+t.relu(t));
		System.out.println("-------------random gen op---------------");
		System.out.println("fill number:" + m.numFill(2.0, true));
		System.out.println("guass random:" + m.randomGaussianFill(6, 2, true));
		System.out.println("ubiform random:" + m.randomUniformFill(0, 10, true));
		
		

	}


}

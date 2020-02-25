package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * this is a double Matrix
 * 
 * @author xuen
 *
 */
public class DoubleMatrix extends Matrix<DoubleMatrix> {
	/**
	 * this is the data
	 */
	private final double[] data;

	public DoubleMatrix(int w, int h, double[] v) {
		super(w, h);
		if (v == null || v.length < w * h)
			throw new RuntimeException("data must be not null and  num more than want");
		this.data = v;
	}

	public DoubleMatrix(int w, int h) {
		super(w, h);
		this.data = new double[w * h];
	}

	@Override
	public DoubleMatrix transpose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix getRange(int rs, int re, int cs, int ce) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix dup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix getRow(int row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix getCol(int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix zeroLikeThis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix tanh(DoubleMatrix out) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix exp(DoubleMatrix out) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix add(DoubleMatrix b, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix addRowVector(DoubleMatrix blas, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix add(double scale, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix sub(DoubleMatrix b, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix mul(DoubleMatrix b, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix mul(double sc, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix div(DoubleMatrix b, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix div(double sc, DoubleMatrix o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix nrm2(char dim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] argMax(char dim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] argMin(char dim) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix mmul(DoubleMatrix b, boolean transA, boolean transB, double alpha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double vdot(DoubleMatrix b) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DoubleMatrix concatColumn(DoubleMatrix y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DoubleMatrix zeros(int col, int rows) {
		return new DoubleMatrix(col, rows);
	}

	@Override
	public double getElement(int row, int column) {
		return get(row, column);
	}

	private double get(int row, int column) {
		return data[row + column * this.rows];
	}

	@Override
	public DoubleMatrix load(DataInputStream in) throws IOException {
		int h = in.readInt();
		int w = in.readInt();
		double[] v = new double[w * h];
		for (int i = 0; i < v.length; i++)
			v[i] = in.readDouble();
		DoubleMatrix m = new DoubleMatrix(w, h, v);
		return m;
	}

	@Override
	public void save(DataOutputStream out) throws IOException {
		out.writeInt(rows);
		out.writeInt(columns);
		for (int i = 0; i < data.length; i++)
			out.writeDouble(data[i]);
	}

}

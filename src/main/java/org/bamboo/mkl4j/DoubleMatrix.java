package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

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
		DoubleMatrix o = new DoubleMatrix(this.rows, this.columns);
		for (int i = 0; i < o.columns; i++)
			MKL.vdCopy(o.rows, this.data, i, this.rows, o.data, o.rows * i, 1);
		return o;
	}

	@Override
	public DoubleMatrix getRange(int rs, int re, int cs, int ce) {
		DoubleMatrix o = new DoubleMatrix(ce - cs, re - rs);
		for (int i = 0; i < o.columns; i++)
			System.arraycopy(data, rs + (cs + i) * this.rows, o.data, o.rows * i, o.rows);
		return o;
	}

	@Override
	public DoubleMatrix dup() {
		return new DoubleMatrix(columns, rows, Arrays.copyOf(data, data.length));
	}

	@Override
	public DoubleMatrix getRow(int row) {
		if (row < 0 || row >= this.rows)
			throw new RuntimeException("index=" + row + " out of row=" + this.rows);

		DoubleMatrix m = new DoubleMatrix(columns, 1);
		MKL.vdCopy(columns, data, row, rows, m.data, 0, 1);
		return m;
	}

	@Override
	public DoubleMatrix getCol(int i) {
		if (i < 0 || i >= this.columns)
			throw new RuntimeException("index=" + i + " out of col=" + this.columns);
		DoubleMatrix m = new DoubleMatrix(1, rows);
		System.arraycopy(data, i * this.rows, m.data, 0, this.rows);
		return m;
	}

	@Override
	public DoubleMatrix log1p(DoubleMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vdLog1p(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public DoubleMatrix ln(DoubleMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vdLn(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public DoubleMatrix sqrt(DoubleMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vdSqrt(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public DoubleMatrix tanh(DoubleMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vdTanh(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public DoubleMatrix sigmoid(DoubleMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vdSigmoid(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public DoubleMatrix exp(DoubleMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vdExp(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public DoubleMatrix relu(DoubleMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		double[] zeros = new double[this.data.length];
		MKL.vdFmax(this.data.length, this.data, 0, zeros, 0, out.data, 0);
		return out;
	}

	@Override
	public DoubleMatrix maxE(DoubleMatrix b, DoubleMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vdFmax(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public DoubleMatrix minE(DoubleMatrix b, DoubleMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vdFmin(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public DoubleMatrix absmaxE(DoubleMatrix b, DoubleMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vdMaxMag(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public DoubleMatrix maxE(char dim) {
		if (dim == 'r') {
			double[] res = new double[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = get(i, MKL.vdAmax(columns, data, i, rows));
			return new DoubleMatrix(1, rows, res);
		}
		double[] res = new double[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = get(MKL.vdAmax(rows, data, i * rows, 1), i);
		return new DoubleMatrix(columns, 1, res);
	}

	@Override
	public DoubleMatrix minE(char dim) {
		if (dim == 'r') {
			double[] res = new double[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = get(i, MKL.vdAmin(columns, data, i, rows));
			return new DoubleMatrix(1, rows, res);
		}
		double[] res = new double[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = get(MKL.vdAmin(rows, data, i * rows, 1), i);
		return new DoubleMatrix(columns, 1, res);
	}

	@Override
	public DoubleMatrix add(DoubleMatrix b, DoubleMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");
		MKL.vdAdd(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public DoubleMatrix addRowVector(DoubleMatrix blas, DoubleMatrix o) {
		if (blas.columns != 1 && blas.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		int size = Math.max(blas.columns, blas.rows);
		if (size != this.columns)
			throw new RuntimeException("matrix row size must eq vector size");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o != this) // copy ori data
			System.arraycopy(this.data, 0, o.data, 0, this.data.length);
		double[] ones = new double[this.rows];
		Arrays.fill(ones, 1.0);
		MKL.vdger(this.rows, this.columns, 1.0f, ones, 0, 1, blas.data, 0, 1, o.data, 0, this.rows);
		return o;
	}

	@Override
	public DoubleMatrix add(double scale, DoubleMatrix o) {
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o != this) // copy ori data
			System.arraycopy(this.data, 0, o.data, 0, this.data.length);
		double[] ones = new double[this.rows + this.columns];
		Arrays.fill(ones, 1.0);
		MKL.vdger(this.rows, this.columns, scale, ones, 0, 1, ones, this.rows, 1, o.data, 0, this.rows);
		return o;
	}

	@Override
	public DoubleMatrix sub(DoubleMatrix b, DoubleMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vdSub(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public DoubleMatrix subRowVector(DoubleMatrix blas, DoubleMatrix o) {
		if (blas.columns != 1 && blas.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		int size = Math.max(blas.columns, blas.rows);
		if (size != this.columns)
			throw new RuntimeException("matrix row size must eq vector size");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o != this) // copy ori data
			System.arraycopy(this.data, 0, o.data, 0, this.data.length);
		double[] ones = new double[this.rows];
		Arrays.fill(ones, 1.0);
		MKL.vdger(this.rows, this.columns, -1.0, ones, 0, 1, blas.data, 0, 1, o.data, 0, this.rows);
		return o;
	}

	@Override
	public DoubleMatrix mul(DoubleMatrix b, DoubleMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");
		MKL.vdMul(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public DoubleMatrix mul(double sc, DoubleMatrix o) {
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o == this) {
			MKL.vdscal(this.data.length, sc, this.data, 0, 1);
			return o;
		}
		MKL.vdaxpy(this.data.length, sc, this.data, 0, 1, o.data, 0, 1);
		return o;
	}

	@Override
	public DoubleMatrix div(DoubleMatrix b, DoubleMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");
		MKL.vdDiv(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public DoubleMatrix div(double sc, DoubleMatrix o) {
		return mul(1.0 / sc, o);
	}

	@Override
	public DoubleMatrix nrm2(char dim) {
		if (dim == 'r') {
			double[] res = new double[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = MKL.vdNrm2(columns, data, i, rows);
			return new DoubleMatrix(1, rows, res);
		}
		double[] res = new double[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = MKL.vdNrm2(rows, data, i * rows, 1);
		return new DoubleMatrix(columns, 1, res);
	}

	@Override
	public int[] argMax(char dim) {
		if (dim == 'r') {
			int[] res = new int[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = MKL.vdAmax(columns, data, i, rows);
			return res;
		}

		int[] res = new int[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = MKL.vdAmax(rows, data, i * rows, 1);
		return res;
	}

	@Override
	public int[] argMin(char dim) {
		if (dim == 'r') {
			int[] res = new int[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = MKL.vdAmin(columns, data, i, rows);
			return res;
		}
		int[] res = new int[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = MKL.vdAmin(rows, data, i * rows, 1);
		return res;
	}

	@Override
	public DoubleMatrix mmul(DoubleMatrix b, boolean transA, boolean transB, double alpha) {
		if (!transA) {
			if (!transB) {
				if (this.columns != b.rows)
					throw new RuntimeException(" a.col=" + this.columns + " not eq b.row=" + b.rows);
				DoubleMatrix o = new DoubleMatrix(b.columns, this.rows);
				MKL.vdgemm('n', 'n', o.rows, o.columns, this.columns, (float) alpha, this.data, 0, this.rows, b.data, 0,
						b.rows, 0.0f, o.data, 0, o.rows);
				return o;
			}

			if (this.columns != b.columns)
				throw new RuntimeException(" a.col=" + this.columns + " not eq b.col=" + b.columns);
			DoubleMatrix o = new DoubleMatrix(b.rows, this.rows);
			MKL.vdgemm('n', 't', o.rows, o.columns, this.columns, (float) alpha, this.data, 0, this.rows, b.data, 0,
					b.rows, 0.0f, o.data, 0, o.rows);
			return o;
		}

		if (!transB) {
			if (this.rows != b.rows)
				throw new RuntimeException(" a.row=" + this.rows + " not eq b.row=" + b.rows);
			DoubleMatrix o = new DoubleMatrix(b.columns, this.columns);
			MKL.vdgemm('t', 'n', o.rows, o.columns, this.rows, (float) alpha, this.data, 0, this.rows, b.data, 0,
					b.rows, 0.0f, o.data, 0, o.rows);
			return o;
		}

		if (this.rows != b.columns)
			throw new RuntimeException(" a.row=" + this.rows + " not eq b.col=" + b.columns);
		DoubleMatrix o = new DoubleMatrix(b.rows, this.columns);
		MKL.vdgemm('t', 't', o.rows, o.columns, this.rows, (float) alpha, this.data, 0, this.rows, b.data, 0, b.rows,
				0.0f, o.data, 0, o.rows);
		return o;
	}

	@Override
	public double vdot(DoubleMatrix b) {
		if (this.columns != 1 && this.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		if (b.columns != 1 && b.rows != 1)
			throw new RuntimeException(" matrix b must be a rank 1 vecctor");
		int size = Math.max(this.columns, this.rows);
		if (size != Math.max(b.columns, b.rows))
			throw new RuntimeException("matrix must be same size");
		return MKL.vddot(size, this.data, 0, 1, b.data, 0, 1);
	}

	@Override
	public DoubleMatrix concatColumn(DoubleMatrix y) {
		if (this.rows != y.rows)
			throw new RuntimeException("matrix x.row=" + this.rows + " not eq y.row=" + y.rows);
		DoubleMatrix m = new DoubleMatrix(this.columns + y.columns, this.rows);
		System.arraycopy(this.data, 0, m.data, 0, this.data.length);
		System.arraycopy(y.data, 0, m.data, this.data.length, y.data.length);
		return m;
	}

	@Override
	public double getElement(int row, int column) {
		return get(row, column);
	}

	private double get(int row, int column) {
		return data[row + column * this.rows];
	}

	/**
	 * load a matrix
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static DoubleMatrix load(DataInputStream in) throws IOException {
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

	@Override
	public DoubleMatrix sum(char dim) {
		if (dim == 'r') {
			double[] res = new double[rows];
			double[] op = new double[columns];
			Arrays.fill(op, 1);
			MKL.vdgemv('n', rows, columns, 1.0f, this.data, 0, this.rows, op, 0, 1, 0, res, 0, 1);
			return new DoubleMatrix(1, rows, res);
		}
		double[] res = new double[columns];
		double[] op = new double[rows];
		Arrays.fill(op, 1);
		MKL.vdgemv('t', rows, columns, 1.0f, this.data, 0, this.rows, op, 0, 1, 0, res, 0, 1);
		return new DoubleMatrix(columns, 1, res);
	}

	@Override
	public DoubleMatrix numFill(double number, boolean dup) {
		DoubleMatrix m = this;
		if (dup)
			m = new DoubleMatrix(this.columns, this.rows);
		Arrays.fill(m.data, number);
		return m;
	}

	@Override
	public DoubleMatrix randomGaussianFill(double mean, double sigma, boolean dup) {
		if (sigma <= 0)
			throw new RuntimeException("sigma=" + sigma + " must be right!");
		DoubleMatrix m = this;
		if (dup)
			m = new DoubleMatrix(this.columns, this.rows);
		MKL.vdRngGaussian(m.data.length, m.data, 0, mean, sigma);
		return m;
	}

	@Override
	public DoubleMatrix randomUniformFill(double a, double b, boolean dup) {
		if (a >= b)
			throw new RuntimeException("a<b needed!");
		DoubleMatrix m = this;
		if (dup)
			m = new DoubleMatrix(this.columns, this.rows);
		MKL.vdRngUniform(m.data.length, m.data, 0, a, b);
		return m;
	}

}

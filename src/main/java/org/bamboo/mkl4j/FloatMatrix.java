package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * float Matrix that store column major <br/>
 * 2020.02.23
 * 
 * @author xuen
 *
 */
public class FloatMatrix extends Matrix<FloatMatrix> {
	/**
	 * the column major data
	 */
	private final float[] data;

	/**
	 * init the matrix from colum major store float
	 * 
	 * @param w
	 * @param h
	 * @param v
	 */
	public FloatMatrix(int w, int h, float[] v) {
		super(w, h);
		if (v == null || v.length < w * h)
			throw new RuntimeException("data must be not null and  num more than want");
		this.data = v;
	}

	/**
	 * init zero matrix that only has size
	 * 
	 * @param w
	 * @param h
	 */
	public FloatMatrix(int w, int h) {
		super(w, h);
		this.data = new float[w * h];
	}

	/**
	 * init the matrix use float 2d array
	 * 
	 * @param m
	 */
	public FloatMatrix(float[][] m) {
		super(m[0].length, m.length);
		this.data = new float[this.columns * this.rows];
		for (int i = 0; i < this.rows; i++) {
			if (m[i].length != this.columns)
				throw new RuntimeException("float array must have the same length!");
			MKL.vsCopy(m[i].length, m[i], 0, 1, this.data, i, this.rows);
		}

	}

	/**
	 * transpose this matrix
	 * 
	 * @return
	 */
	public FloatMatrix transpose() {
		FloatMatrix o = new FloatMatrix(this.rows, this.columns);
		for (int i = 0; i < o.columns; i++)
			MKL.vsCopy(o.rows, this.data, i, this.rows, o.data, o.rows * i, 1);
		return o;
	}

	/**
	 * this data to 2 dim array
	 * 
	 * @return
	 */
	public float[][] to2DArray() {
		float[][] dat = new float[this.rows][this.columns];
		for (int i = 0; i < dat.length; i++)
			MKL.vsCopy(dat[i].length, this.data, i, this.rows, dat[i], 0, 1);
		return dat;
	}

	/**
	 * this data to column major array
	 * 
	 * @return
	 */
	public float[] toArray() {
		return this.data;
	}

	/**
	 * Get elements from rows <tt>rs</tt> to <tt>re</tt> and columns <tt>cs</tt> to
	 * <tt>ce</tt>.
	 */
	public FloatMatrix getRange(int rs, int re, int cs, int ce) {
		FloatMatrix o = new FloatMatrix(ce - cs, re - rs);
		for (int i = 0; i < o.columns; i++)
			System.arraycopy(data, rs + (cs + i) * this.rows, o.data, o.rows * i, o.rows);
		return o;
	}

	public FloatMatrix dup() {
		return new FloatMatrix(columns, rows, Arrays.copyOf(data, data.length));
	}

	public FloatMatrix getRow(int i) {
		if (i < 0 || i >= this.rows)
			throw new RuntimeException("index=" + i + " out of row=" + this.rows);

		FloatMatrix m = new FloatMatrix(columns, 1);
		MKL.vsCopy(columns, data, i, rows, m.data, 0, 1);
		return m;
	}

	public FloatMatrix getCol(int i) {
		if (i < 0 || i >= this.columns)
			throw new RuntimeException("index=" + i + " out of col=" + this.columns);
		FloatMatrix m = new FloatMatrix(1, rows);
		System.arraycopy(data, i * this.rows, m.data, 0, this.rows);
		return m;
	}

	public float get(int i, int j) {
		return this.data[i + this.rows * j];
	}

	@Override
	public FloatMatrix ln(FloatMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vsLn(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public FloatMatrix sqrt(FloatMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vsSqrt(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public FloatMatrix log1p(FloatMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vsLog1p(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	/**
	 * tanh use
	 * 
	 * @param val
	 * @param out
	 */
	public FloatMatrix tanh(FloatMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vsTanh(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public FloatMatrix sigmoid(FloatMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vsSigmoid(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	/**
	 * 
	 * @param val
	 * @param out
	 */
	public FloatMatrix exp(FloatMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		MKL.vsExp(this.data.length, this.data, 0, out.data, 0);
		return out;
	}

	@Override
	public FloatMatrix relu(FloatMatrix out) {
		if (out.columns != this.columns || this.rows != out.rows)
			throw new RuntimeException("out matrix size must eq val size");
		float[] zeros = new float[this.data.length];
		MKL.vsFmax(this.data.length, this.data, 0, zeros, 0, out.data, 0);
		return out;
	}

	/**
	 * o=this+b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public FloatMatrix add(FloatMatrix b, FloatMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");
		MKL.vsAdd(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	/**
	 * o
	 * 
	 * @param blas
	 * @return
	 */
	public FloatMatrix addRowVector(FloatMatrix blas, FloatMatrix o) {
		if (blas.columns != 1 && blas.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		int size = Math.max(blas.columns, blas.rows);
		if (size != this.columns)
			throw new RuntimeException("matrix row size must eq vector size");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o != this) // copy ori data
			System.arraycopy(this.data, 0, o.data, 0, this.data.length);
		float[] ones = new float[this.rows];
		Arrays.fill(ones, 1.0f);
		MKL.vsger(this.rows, this.columns, 1.0f, ones, 0, 1, blas.data, 0, 1, o.data, 0, this.rows);
		return o;
	}

	@Override
	public FloatMatrix maxE(FloatMatrix b, FloatMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vsFmax(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public FloatMatrix minE(FloatMatrix b, FloatMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vsFmin(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	@Override
	public FloatMatrix maxE(char dim) {
		if (dim == 'r') {
			float[] res = new float[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = get(i, MKL.vsAmax(columns, data, i, rows));
			return new FloatMatrix(1, rows, res);
		}
		float[] res = new float[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = get(MKL.vsAmax(rows, data, i * rows, 1), i);
		return new FloatMatrix(columns, 1, res);
	}

	@Override
	public FloatMatrix minE(char dim) {
		if (dim == 'r') {
			float[] res = new float[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = get(i, MKL.vsAmin(columns, data, i, rows));
			return new FloatMatrix(1, rows, res);
		}
		float[] res = new float[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = get(MKL.vsAmin(rows, data, i * rows, 1), i);
		return new FloatMatrix(columns, 1, res);
	}

	@Override
	public FloatMatrix absmaxE(FloatMatrix b, FloatMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vsMaxMag(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	/**
	 * o=this+scale
	 * 
	 * @param a
	 * @param scale
	 * @param o
	 */
	public FloatMatrix add(double scale, FloatMatrix o) {
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o != this) // copy ori data
			System.arraycopy(this.data, 0, o.data, 0, this.data.length);
		float[] ones = new float[this.rows + this.columns];
		Arrays.fill(ones, 1.0f);
		MKL.vsger(this.rows, this.columns, (float) scale, ones, 0, 1, ones, this.rows, 1, o.data, 0, this.rows);
		return o;
	}

	/**
	 * o=this-b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public FloatMatrix sub(FloatMatrix b, FloatMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");

		MKL.vsSub(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	/**
	 * o
	 * 
	 * @param blas
	 * @return
	 */
	public FloatMatrix subRowVector(FloatMatrix blas, FloatMatrix o) {
		if (blas.columns != 1 && blas.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		int size = Math.max(blas.columns, blas.rows);
		if (size != this.columns)
			throw new RuntimeException("matrix row size must eq vector size");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o != this) // copy ori data
			System.arraycopy(this.data, 0, o.data, 0, this.data.length);
		float[] ones = new float[this.rows];
		Arrays.fill(ones, 1.0f);
		MKL.vsger(this.rows, this.columns, -1.0f, ones, 0, 1, blas.data, 0, 1, o.data, 0, this.rows);
		return o;
	}

	/**
	 * o=this*b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public FloatMatrix mul(FloatMatrix b, FloatMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");
		MKL.vsMul(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	/**
	 * o=this*sc
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public FloatMatrix mul(double sc, FloatMatrix o) {
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o == this) {
			MKL.vsscal(this.data.length, (float) sc, this.data, 0, 1);
			return o;
		}
		MKL.vsaxpy(this.data.length, (float) sc, this.data, 0, 1, o.data, 0, 1);
		return o;
	}

	/**
	 * o=this/b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public FloatMatrix div(FloatMatrix b, FloatMatrix o) {
		if (this.columns != b.columns || this.rows != b.rows)
			throw new RuntimeException("a and b matrix size must be same");
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");
		MKL.vsDiv(this.data.length, this.data, 0, b.data, 0, o.data, 0);
		return o;
	}

	/**
	 * o=this/sc
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public FloatMatrix div(double sc, FloatMatrix o) {
		return mul(1.0 / sc, o);
	}

	/**
	 * calculate the nromal 2 dist in dim
	 * 
	 * @param a
	 * @param dim
	 * @return
	 */
	public FloatMatrix nrm2(char dim) {
		if (dim == 'r') {
			float[] res = new float[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = MKL.vsNrm2(columns, data, i, rows);
			return new FloatMatrix(1, rows, res);
		}
		float[] res = new float[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = MKL.vsNrm2(rows, data, i * rows, 1);
		return new FloatMatrix(columns, 1, res);
	}

	@Override
	public FloatMatrix softmax(char dim, FloatMatrix o) {
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq a or b size");
		dim=dim=='r'?'c':'r';
		FloatMatrix rmax = this.maxE(dim);
		this.addVector(dim=='r'?'c':'r', -1.0, rmax, o);
		MKL.vsExp(o.data.length, o.data, 0, o.data, 0);
		float[] sum = o.sum(dim).data;
		if (dim == 'r') {
			for (int i = 0; i < rows; i++)
				MKL.vsscal(o.columns, 1.0f/sum[i], o.data, i, rows);
		} else {
			for (int i = 0; i < columns; i++)
				MKL.vsscal(o.rows, 1.0f/sum[i], o.data, i * rows, 1);
		}

		return o;
	}

	/**
	 * o=alpha*blas+this(every vector along dim)
	 * 
	 * @param dim
	 * @param alpha
	 * @param blas
	 * @param o
	 */
	public FloatMatrix addVector(char dim, double alpha, FloatMatrix blas, FloatMatrix o) {
		if (blas.columns != 1 && blas.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		int size = Math.max(blas.columns, blas.rows);
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if ('r' == dim) {
			if (size != this.columns)
				throw new RuntimeException("matrix column size must eq vector size");
			if (o != this) // copy ori data
				System.arraycopy(this.data, 0, o.data, 0, this.data.length);
			float[] ones = new float[this.rows];
			Arrays.fill(ones, 1.0f);
			MKL.vsger(this.rows, this.columns, (float) alpha, ones, 0, 1, blas.data, 0, 1, o.data, 0, this.rows);
			return o;
		}
		if (size != this.rows)
			throw new RuntimeException("matrix row size must eq vector size");
		if (o != this) // copy ori data
			System.arraycopy(this.data, 0, o.data, 0, this.data.length);
		float[] ones = new float[this.columns];
		Arrays.fill(ones, 1.0f);
		MKL.vsger(this.rows, this.columns, (float) alpha, blas.data, 0, 1, ones, 0, 1, o.data, 0, this.rows);
		return o;

	}

	/**
	 * the max index every row or column
	 * 
	 * @param a
	 * @param dim
	 * @return
	 */
	public int[] argMax(char dim) {
		if (dim == 'r') {
			int[] res = new int[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = MKL.vsAmax(columns, data, i, rows);
			return res;
		}

		int[] res = new int[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = MKL.vsAmax(rows, data, i * rows, 1);
		return res;

	}

	/**
	 * the min index every row or column
	 * 
	 * @param a
	 * @param dim
	 * @return
	 */
	public int[] argMin(char dim) {
		if (dim == 'r') {
			int[] res = new int[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = MKL.vsAmin(columns, data, i, rows);
			return res;
		}
		int[] res = new int[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = MKL.vsAmin(rows, data, i * rows, 1);
		return res;

	}

	/**
	 * o=alpha*a×b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public FloatMatrix mmul(FloatMatrix b, boolean transA, boolean transB, double alpha) {
		if (!transA) {
			if (!transB) {
				if (this.columns != b.rows)
					throw new RuntimeException(" a.col=" + this.columns + " not eq b.row=" + b.rows);
				FloatMatrix o = new FloatMatrix(b.columns, this.rows);
				MKL.vsgemm('n', 'n', o.rows, o.columns, this.columns, (float) alpha, this.data, 0, this.rows, b.data, 0,
						b.rows, 0.0f, o.data, 0, o.rows);
				return o;
			}

			if (this.columns != b.columns)
				throw new RuntimeException(" a.col=" + this.columns + " not eq b.col=" + b.columns);
			FloatMatrix o = new FloatMatrix(b.rows, this.rows);
			MKL.vsgemm('n', 't', o.rows, o.columns, this.columns, (float) alpha, this.data, 0, this.rows, b.data, 0,
					b.rows, 0.0f, o.data, 0, o.rows);
			return o;
		}

		if (!transB) {
			if (this.rows != b.rows)
				throw new RuntimeException(" a.row=" + this.rows + " not eq b.row=" + b.rows);
			FloatMatrix o = new FloatMatrix(b.columns, this.columns);
			MKL.vsgemm('t', 'n', o.rows, o.columns, this.rows, (float) alpha, this.data, 0, this.rows, b.data, 0,
					b.rows, 0.0f, o.data, 0, o.rows);
			return o;
		}

		if (this.rows != b.columns)
			throw new RuntimeException(" a.row=" + this.rows + " not eq b.col=" + b.columns);
		FloatMatrix o = new FloatMatrix(b.rows, this.columns);
		MKL.vsgemm('t', 't', o.rows, o.columns, this.rows, (float) alpha, this.data, 0, this.rows, b.data, 0, b.rows,
				0.0f, o.data, 0, o.rows);
		return o;

	}

	/**
	 * vector a dot vector b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public double vdot(FloatMatrix b) {
		if (this.columns != 1 && this.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		if (b.columns != 1 && b.rows != 1)
			throw new RuntimeException(" matrix b must be a rank 1 vecctor");
		int size = Math.max(this.columns, this.rows);
		if (size != Math.max(b.columns, b.rows))
			throw new RuntimeException("matrix must be same size");
		return MKL.vsdot(size, this.data, 0, 1, b.data, 0, 1);
	}

	/**
	 * concat two matrix into same that two matrix must row same
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public FloatMatrix concatColumn(FloatMatrix y) {
		if (this.rows != y.rows)
			throw new RuntimeException("matrix x.row=" + this.rows + " not eq y.row=" + y.rows);
		FloatMatrix m = new FloatMatrix(this.columns + y.columns, this.rows);
		System.arraycopy(this.data, 0, m.data, 0, this.data.length);
		System.arraycopy(y.data, 0, m.data, this.data.length, y.data.length);
		return m;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columns;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + rows;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FloatMatrix other = (FloatMatrix) obj;
		if (columns != other.columns)
			return false;
		if (!Arrays.equals(data, other.data))
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}

	@Override
	public double getElement(int row, int column) {
		return get(row, column);
	}

	public static FloatMatrix load(DataInputStream in) throws IOException {
		int h = in.readInt();
		int w = in.readInt();
		float[] v = new float[w * h];
		for (int i = 0; i < v.length; i++)
			v[i] = in.readFloat();
		FloatMatrix m = new FloatMatrix(w, h, v);
		return m;
	}

	@Override
	public void save(DataOutputStream out) throws IOException {
		out.writeInt(rows);
		out.writeInt(columns);
		for (int i = 0; i < data.length; i++)
			out.writeFloat(data[i]);
	}

	@Override
	public FloatMatrix sum(char dim) {
		if (dim == 'r') {
			float[] res = new float[rows];
			float[] op = new float[columns];
			Arrays.fill(op, 1);
			MKL.vsgemv('n', rows, columns, 1.0f, this.data, 0, this.rows, op, 0, 1, 0, res, 0, 1);
			return new FloatMatrix(1, rows, res);
		}
		float[] res = new float[columns];
		float[] op = new float[rows];
		Arrays.fill(op, 1);
		MKL.vsgemv('t', rows, columns, 1.0f, this.data, 0, this.rows, op, 0, 1, 0, res, 0, 1);
		return new FloatMatrix(columns, 1, res);
	}

	/**
	 * get zeros matrix
	 * 
	 * @param col
	 * @param rows
	 * @return
	 */
	@Override
	public FloatMatrix numFill(double num, boolean dup) {
		FloatMatrix m = this;
		if (dup)
			m = new FloatMatrix(this.columns, this.rows);
		Arrays.fill(m.data, (float) num);
		return m;
	}

	@Override
	public FloatMatrix randomGaussianFill(double mean, double sigma, boolean dup) {
		if (sigma <= 0)
			throw new RuntimeException("sigma=" + sigma + " must be right!");
		FloatMatrix m = this;
		if (dup)
			m = new FloatMatrix(this.columns, this.rows);
		MKL.vsRngGaussian(m.data.length, m.data, 0, (float) mean, (float) sigma);
		return m;
	}

	@Override
	public FloatMatrix randomUniformFill(double a, double b, boolean dup) {
		if (a >= b)
			throw new RuntimeException("a<b needed!");
		FloatMatrix m = this;
		if (dup)
			m = new FloatMatrix(this.columns, this.rows);
		MKL.vsRngUniform(m.data.length, m.data, 0, (float) a, (float) b);
		return m;
	}

}

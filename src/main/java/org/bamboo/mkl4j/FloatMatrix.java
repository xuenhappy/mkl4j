package org.bamboo.mkl4j;

import java.util.Arrays;

/**
 * float Matrix that store column major <br/>
 * 2020.02.23
 * 
 * @author xuen
 *
 */
public class FloatMatrix {
	/**
	 * row of this matrix
	 */
	public final int rows;
	/**
	 * columns of this matrix
	 */
	public final int columns;

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
		if (v == null || v.length < w * h)
			throw new RuntimeException("data must be not null and  num more than want");
		this.data = v;
		this.columns = w;
		this.rows = h;
	}

	/**
	 * init zero matrix that only has size
	 * 
	 * @param w
	 * @param h
	 */
	public FloatMatrix(int w, int h) {
		this.columns = w;
		this.rows = h;
		this.data = new float[w * h];
	}

	/**
	 * init the matrix use float 2d array
	 * 
	 * @param m
	 */
	public FloatMatrix(float[][] m) {
		int mlen = 0;
		for (int i = 0; i < m.length; i++)
			if (mlen < m[0].length)
				mlen = m[0].length;
		this.columns = mlen;
		this.rows = m.length;
		this.data = new float[this.columns * this.rows];
		for (int i = 0; i < this.rows; i++)
			MKL.vsCopy(m[i].length, m[i], 0, 1, this.data, i, this.rows);

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
		FloatMatrix m = new FloatMatrix(rows, 1);
		System.arraycopy(data, i * this.rows, m.data, 0, this.rows);
		return m;
	}

	public float get(int i, int j) {
		return this.data[i + this.rows * j];
	}

	/**
	 * get zeros marix
	 * 
	 * @param val
	 * @return
	 */

	public FloatMatrix zeroLikeThis() {
		return new FloatMatrix(this.columns, this.rows);
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
		for (int i = 0; i < this.rows; i++)// add vector
			MKL.vsaxpy(this.columns, 1.0f, blas.data, 0, 1, o.data, i, o.rows);
		return o;
	}

	/**
	 * o=this+scale
	 * 
	 * @param a
	 * @param scale
	 * @param o
	 */
	public FloatMatrix add(float scale, FloatMatrix o) {
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		for (int i = 0; i < o.data.length; i++)
			o.data[i] = this.data[i] + scale;
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
	public FloatMatrix mul(float sc, FloatMatrix o) {
		if (o.columns != this.columns || o.rows != this.rows)
			throw new RuntimeException("out matrix size must eq val size");
		if (o == this) {
			MKL.vsscal(this.data.length, sc, this.data, 0, 1);
			return o;
		}
		MKL.vsaxpy(this.data.length, sc, this.data, 0, 1, o.data, 0, 1);
		return o;
	}

	/**
	 * calculate the nromal 2 dist in dim
	 * 
	 * @param a
	 * @param dim
	 * @return
	 */
	public float[] nrm2(char dim) {
		if (dim == 'r') {
			float[] res = new float[rows];
			for (int i = 0; i < res.length; i++)
				res[i] = MKL.vsNrm2(columns, data, i, rows);
			return res;
		}
		float[] res = new float[columns];
		for (int i = 0; i < res.length; i++)
			res[i] = MKL.vsNrm2(rows, data, i * rows, 1);
		return res;
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

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(String.format("FloatMatrix [size=(%d*%d)]", this.rows, this.columns));
		buf.append("\n[");
		boolean skipr = false;
		for (int i = 0; i < rows; i++) {
			if (i > 10 && i < rows - 6) {
				if (skipr)
					continue;
				skipr = true;
				buf.append("..............................\n");
			}
			buf.append("[");
			boolean skipc = false;
			for (int j = 0; j < columns; j++) {
				if (j > 8 && j < columns - 5) {
					if (skipc)
						continue;
					skipc = true;
					buf.append("....,");
				}
				buf.append(get(i, j)).append(",");
			}
			if (this.columns > 0)
				buf.setLength(buf.length() - 1);
			buf.append("]\n");
		}
		if (this.rows > 0)
			buf.setLength(buf.length() - 1);
		buf.append("]");
		return buf.toString();
	}

	/****
	 * 
	 * ===================<br/>
	 * static method <br/>
	 * ===================<br/>
	 * 
	 */

	/**
	 * get zeros matrix
	 * 
	 * @param col
	 * @param rows
	 * @return
	 */
	public static FloatMatrix zeros(int col, int rows) {
		return new FloatMatrix(col, rows);
	}

	/**
	 * o=alpha*a×b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public static FloatMatrix mmul(FloatMatrix a, FloatMatrix b, boolean transA, boolean transB, float alpha) {
		if (!transA) {
			if (!transB) {
				if (a.columns != b.rows)
					throw new RuntimeException(" a.col=" + a.columns + " not eq b.row=" + b.rows);
				FloatMatrix o = new FloatMatrix(b.columns, a.rows);
				MKL.vsgemm('n', 'n', o.rows, o.columns, a.columns, alpha, a.data, 0, a.rows, b.data, 0, b.rows, 0.0f,
						o.data, 0, o.rows);
				return o;
			}

			if (a.columns != b.columns)
				throw new RuntimeException(" a.col=" + a.columns + " not eq b.col=" + b.columns);
			FloatMatrix o = new FloatMatrix(b.rows, a.rows);
			MKL.vsgemm('n', 't', o.rows, o.columns, a.columns, alpha, a.data, 0, a.rows, b.data, 0, b.columns, 0.0f,
					o.data, 0, o.rows);
			return o;
		}

		if (!transB) {
			if (a.rows != b.rows)
				throw new RuntimeException(" a.row=" + a.rows + " not eq b.row=" + b.rows);
			FloatMatrix o = new FloatMatrix(b.columns, a.columns);
			MKL.vsgemm('t', 'n', o.rows, o.columns, a.rows, alpha, a.data, 0, a.columns, b.data, 0, b.rows, 0.0f,
					o.data, 0, a.columns);
			return o;
		}

		if (a.rows != b.columns)
			throw new RuntimeException(" a.row=" + a.rows + " not eq b.col=" + b.columns);
		FloatMatrix o = new FloatMatrix(b.rows, a.columns);
		MKL.vsgemm('t', 't', o.rows, o.columns, a.rows, alpha, a.data, 0, a.columns, b.data, 0, b.columns, 0.0f, o.data,
				0, a.columns);
		return o;

	}

	/**
	 * vector a dot vector b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static float dot(FloatMatrix a, FloatMatrix b) {
		if (a.columns != 1 && a.rows != 1)
			throw new RuntimeException(" matrix a must be a rank 1 vecctor");
		if (b.columns != 1 && b.rows != 1)
			throw new RuntimeException(" matrix b must be a rank 1 vecctor");
		int size = Math.max(a.columns, a.rows);
		if (size != Math.max(b.columns, b.rows))
			throw new RuntimeException("matrix must be same size");
		return MKL.vsdot(size, a.data, 0, 1, b.data, 0, 1);
	}

	/**
	 * concat two matrix into same that two matrix must row same
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static FloatMatrix concatColumn(FloatMatrix x, FloatMatrix y) {
		if (x.rows != y.rows)
			throw new RuntimeException("matrix x.row=" + x.rows + " not eq y.row=" + y.rows);
		FloatMatrix m = new FloatMatrix(x.columns + y.columns, x.rows);
		System.arraycopy(x.data, 0, m.data, 0, x.data.length);
		System.arraycopy(y.data, 0, m.data, x.data.length, y.data.length);
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

}

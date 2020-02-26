package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * matrix out use
 * 
 * @author xuen
 *
 */
public abstract class Matrix<T> {
	/**
	 * row of this matrix
	 */
	public final int rows;
	/**
	 * columns of this matrix
	 */
	public final int columns;

	/**
	 * init matrix size
	 * 
	 * @param w
	 * @param h
	 */
	public Matrix(int w, int h) {
		if (w <= 0 || h <= 0)
			throw new RuntimeException(" w and h must be right number!");
		this.columns = w;
		this.rows = h;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isScale() {
		return this.columns == 1 && this.rows == 1;
	}

	public boolean isVector() {
		return this.columns == 1 || this.rows == 1;
	}

	/**
	 * transpose this matrix
	 * 
	 * @return
	 */
	public abstract T transpose();

	/**
	 * Get elements from rows <tt>rs</tt> to <tt>re</tt> and columns <tt>cs</tt> to
	 * <tt>ce</tt>.
	 */
	public abstract T getRange(int rs, int re, int cs, int ce);

	/**
	 * copy this matrix
	 * 
	 * @return
	 */
	public abstract T dup();

	/**
	 * get row of this matrix
	 * 
	 * @param i
	 * @return
	 */

	public abstract T getRow(int row);

	/**
	 * get column of this matrix
	 * 
	 * @param column
	 * @return
	 */
	public abstract T getCol(int column);

	/**
	 * tanh use
	 * 
	 * @param val
	 * @param out
	 */
	public abstract T tanh(T out);
	
	/**
	 * relu func
	 * @param out
	 * @return
	 */
	public abstract T relu(T out);
	

	/**
	 * 
	 * @param val
	 * @param out
	 */
	public abstract T exp(T out);
	
	/**
	 * o=this+b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T maxE(T b, T o);
	
	
	
	/**
	 * o=this+b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T minE(T b, T o);
	
	
	
	/**
	 * o=this+b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T absmaxE(T b, T o);
	
	

	/**
	 * o=this+b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T add(T b, T o);

	/**
	 * o
	 * 
	 * @param blas
	 * @return
	 */
	public abstract T addRowVector(T blas, T o);

	/**
	 * o=this+scale
	 * 
	 * @param a
	 * @param scale
	 * @param o
	 */
	public abstract T add(double scale, T o);

	/**
	 * o=this-b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T sub(T b, T o);

	/**
	 * o=this*b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T mul(T b, T o);

	/**
	 * o=this*sc
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T mul(double sc, T o);

	/**
	 * o=this/b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T div(T b, T o);

	/**
	 * o=this/sc
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T div(double sc, T o);

	/**
	 * sum of along row or column dim
	 * 
	 * @param dim 'r' along row ,'c' along column
	 * @return
	 */
	public abstract T sum(char dim);

	/**
	 * cal this nrm of the matrix
	 * 
	 * @param dim 'r' along row ,'c' along column
	 * @return
	 */
	public abstract T nrm2(char dim);

	/**
	 * the max index every row or column
	 * 
	 * @param a
	 * @param dim 'r' along row ,'c' along column
	 * @return
	 */
	public abstract int[] argMax(char dim);

	/**
	 * the min index every row or column
	 * 
	 * @param a
	 * @param dim 'r' along row ,'c' along column
	 * @return
	 */
	public abstract int[] argMin(char dim);

	/**
	 * o=alpha*a×b
	 * 
	 * @param a
	 * @param b
	 * @param o
	 */
	public abstract T mmul(T b, boolean transA, boolean transB, double alpha);

	/**
	 * vector a dot vector b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public abstract double vdot(T b);

	/**
	 * concat two matrix into same that two matrix must row same
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract T concatColumn(T y);

	/**
	 * fill matrix use given number
	 * 
	 * @param col
	 * @param rows
	 * @return
	 */

	public abstract T numFill(double num, boolean dup);

	/**
	 * fill this matrix use Gaussian method
	 * 
	 * @param col
	 * @param row
	 * @return
	 */
	public abstract T randomGaussianFill(double mean, double sigma, boolean dup);

	/**
	 * fill this matrix use Uniform method[a,b)
	 * 
	 * @param col
	 * @param row
	 * @param a
	 * @param b
	 * @return
	 */
	public abstract T randomUniformFill(double a, double b, boolean dup);

	/**
	 * get the elment in
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public abstract double getElement(int row, int column);

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(String.format("%s[size=(%d*%d)]\n", this.getClass().getSimpleName(), this.rows, this.columns));
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
				buf.append(String.format("%g", getElement(i, j))).append(",");
			}
			if (this.columns > 0)
				buf.setLength(buf.length() - 1);
			buf.append("]\n");
		}
		if (this.rows > 0)
			buf.setLength(buf.length() - 1);
		return buf.toString();
	}

	/**
	 * load the data from inputstream
	 * 
	 * @param in
	 * @throws IOException
	 */
	public abstract T load(DataInputStream in) throws IOException;

	/**
	 * save this to stream
	 * 
	 * @param out
	 * @throws IOException
	 */
	public abstract void save(DataOutputStream out) throws IOException;

}

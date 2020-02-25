package org.bamboo.mkl4j;

public interface MatrixFunc<T extends Matrix<T>> {
	/**
	 * @param val
	 * @return
	 */
	public T call(T input);
	
	/**
	 * the name of this activation
	 */
	public String name();

}

package org.bamboo.mkl4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * this is the net
 * 
 * @author xuen
 *
 */
public interface NeuralNetwork<T extends Matrix<T>> {
	public void load(InputStream in) throws IOException;
	public void save(OutputStream out) throws IOException;

}

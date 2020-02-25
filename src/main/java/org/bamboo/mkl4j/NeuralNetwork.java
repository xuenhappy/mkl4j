package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * this is the net
 * 
 * @author xuen
 *
 */
public interface NeuralNetwork<T extends Matrix<T>> {
	public void load(DataInputStream in) throws IOException;
	public void save(DataOutputStream out) throws IOException;

}

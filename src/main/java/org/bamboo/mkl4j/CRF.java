package org.bamboo.mkl4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * CRF Layer
 * 
 * @author xuen
 *
 * @param <T>
 */
public class CRF<T extends Matrix<T>> implements NeuralNetwork {

	/**
	 * tag trans param
	 */
	private T trans;
	/**
	 * tag embedding map
	 */
	private T map;

	/**
	 * decode the data
	 */
	@SuppressWarnings("unchecked")
	public int[] viterbi_decode(T intput) {
		T score = intput.mmul(map,false,false,1.0f);
		T[] trellis = (T[]) Array.newInstance(trans.getClass(), score.rows);
		int[][] backpointers = new int[score.rows][];
		trellis[0] = score.getRow(0);
		for (int t = 1; t < score.rows; t++) {
			T v = trans.addRowVector(trellis[t - 1], trans.numFill(0, true));
			backpointers[t] = v.argMax('c');
			trellis[t] = score.getRow(t);
			trellis[t].add(v.maxE('c'), trellis[t]);
		}
		int[] viterbi = new int[score.rows];
		viterbi[viterbi.length - 1] = trellis[viterbi.length - 1].argMax('r')[0];
		for (int j = viterbi.length - 2; j >= 0; j--)
			viterbi[j] = backpointers[j + 1][viterbi[j + 1]];
		// float viterbi_score=trellis[trellis.length-1].max();
		return viterbi;
	}

	@Override
	public void load(DataInputStream in) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(DataOutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

}

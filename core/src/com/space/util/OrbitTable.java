package com.space.util;

import java.io.IOException;

/**
 * OrbitTable.
 */
public class OrbitTable {

	private static float[][] table;

	static {
		initializeTable();
	}

	private static void initializeTable() {
		try {
			table = AttributeReader.getOrbitTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return orbit table as a float[][]
	 */
	public static float[][] getTable() {
		return table;
	}

}

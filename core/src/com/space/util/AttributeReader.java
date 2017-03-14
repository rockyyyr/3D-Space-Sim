package com.space.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.space.universe.solarsystem.Moon;
import com.space.universe.solarsystem.Planet;

/**
 * AttributeReader.
 */
public class AttributeReader {

	public static final String PLANET_FILE = "data/planets.dat";
	public static final String MOON_FILE = "data/moons.dat";
	public static final String ORBIT_FILE = "data/orbitLocationTable.dat";
	public static final String FIELD_DELIMITER = "\\|";
	public static final int BUFFER_SIZE = 8192;

	/**
	 * @return An arraylist of planets created using data from 'planets.dat'
	 * @throws IOException
	 */
	public static ArrayList<Planet> fetchPlanets() throws IOException {
		FileHandle file = Gdx.files.internal(PLANET_FILE);
		BufferedReader reader = file.reader(BUFFER_SIZE);

		reader.readLine(); // skip first line

		ArrayList<Planet> planets = new ArrayList<Planet>();

		String line;
		while ((line = reader.readLine()) != null) {
			planets.add(createPlanetFromData(parseLine(line)));
		}
		reader.close();
		return planets;
	}

	/**
	 * @param planet
	 *            The host planet for this moon
	 * @return an arraylist of moons created using data from 'moons.dat'
	 * @throws IOException
	 */
	public static ArrayList<Moon> fetchMoon(Planet planet) throws IOException {
		FileHandle file = Gdx.files.internal(MOON_FILE);
		BufferedReader reader = file.reader(BUFFER_SIZE);

		reader.readLine(); // skip first line

		ArrayList<Moon> moons = new ArrayList<Moon>();

		String line;
		while ((line = reader.readLine()) != null) {
			String[] attributes = parseLine(line);

			if (attributes[0].equals(planet.getName()))
				moons.add(createMoonFromData(attributes, planet));
		}
		reader.close();
		return moons;
	}

	/**
	 * Fetches data from 'orbitLocationTable.dat'. Values represent coordinates of a circle on a flat plane.
	 * Returned values in the 2d array are array[i][0] = x, array[i][1] = y, array[i][2] = z
	 * 
	 * @return Float array containing orbit location values
	 * @throws IOException
	 */
	public static float[][] getOrbitTable() throws IOException {
		FileHandle file = Gdx.files.internal(ORBIT_FILE);
		BufferedReader reader = file.reader(BUFFER_SIZE);

		float[][] table = new float[130][3];

		for (int i = 0; i < table.length; i++)
			table[i] = parseTableLine(reader.readLine());

		reader.close();
		return table;
	}

	private static String[] parseLine(String line) {
		line = line.replaceAll(" ", "");
		return line.split(FIELD_DELIMITER);
	}

	private static float[] parseTableLine(String line) {
		line = line.replaceAll(" ", "");

		String[] temp = line.split(FIELD_DELIMITER);
		float[] temp1 = new float[temp.length];

		for (int i = 0; i < temp1.length; i++) {
			temp1[i] = Float.valueOf(temp[i]);
		}
		return temp1;
	}

	private static Planet createPlanetFromData(String[] attributes) {
		String name = attributes[0];
		float diameter = Float.valueOf(attributes[1]);
		float distance = Float.valueOf(attributes[2]);
		// float orbitalPeriod = Float.valueOf(attributes[3]);
		float dayLength = Float.valueOf(attributes[4]);
		float lightDistance = Float.valueOf(attributes[5]);
		boolean hasMoon = Boolean.valueOf(attributes[6]);
		return new Planet(name, diameter, distance, dayLength, lightDistance, hasMoon);
	}

	private static Moon createMoonFromData(String[] attributes, Planet planet) {
		String name = attributes[1];
		float scale = Float.valueOf(attributes[2]);
		float distance = Float.valueOf(attributes[3]);
		return new Moon(name, scale, distance, planet);
	}

}

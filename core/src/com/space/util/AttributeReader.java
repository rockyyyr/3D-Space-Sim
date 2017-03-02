package com.space.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.space.universe.solarsystem.Moon;
import com.space.universe.solarsystem.Planet;

/**
 * AttributeReader.
 */
public class AttributeReader {

	public static final String PLANET_FILE = "src/com/space/desktop/planets.dat";
	public static final String MOON_FILE = "src/com/space/desktop/moons.dat";
	public static final String ORBIT_FILE = "src/com/space/desktop/orbitLocationTable.dat";
	public static final String FIELD_DELIMITER = "\\|";

	public static ArrayList<Planet> fetchPlanets() throws FileNotFoundException, IOException {
		ArrayList<Planet> planets = new ArrayList<Planet>();
		BufferedReader reader = new BufferedReader(new FileReader(PLANET_FILE));

		reader.readLine();

		String line;

		while ((line = reader.readLine()) != null) {
			planets.add(createPlanetFromData(parseLine(line)));
		}

		reader.close();
		return planets;
	}

	public static ArrayList<Moon> fetchMoon(Planet planet) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(MOON_FILE));
		reader.readLine();

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
		float orbitalPeriod = Float.valueOf(attributes[3]);
		float dayLength = Float.valueOf(attributes[4]);
		float lightDistance = Float.valueOf(attributes[5]);
		boolean hasMoon = Boolean.valueOf(attributes[6]);
		return new Planet(name, diameter, distance, orbitalPeriod, dayLength, lightDistance, hasMoon);
	}

	private static Moon createMoonFromData(String[] attributes, Planet planet) {
		String name = attributes[1];
		float scale = Float.valueOf(attributes[2]);
		float distance = Float.valueOf(attributes[3]);
		return new Moon(name, scale, distance, 0, 0, 0, planet);
	}

	public static float[][] getOrbitTable() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(ORBIT_FILE));
		float[][] table = new float[130][3];

		for (int i = 0; i < table.length; i++)
			table[i] = parseTableLine(reader.readLine());

		reader.close();
		return table;
	}

}

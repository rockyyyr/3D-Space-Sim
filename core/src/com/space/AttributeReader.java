package com.space;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.space.universe.solarsystem.planets.Planet;

/**
 * AttributeReader.
 */
public class AttributeReader {

	public static final String FILENAME = "planets.dat";
	public static final String FIELD_DELIMITER = "\\|";
	public static final int NUM_OF_PLANETS = 9;

	private AttributeReader() {
	}

	public static Planet[] fetchPlanets() throws FileNotFoundException, IOException {
		Planet[] planets = new Planet[NUM_OF_PLANETS];
		BufferedReader reader = readFile();

		String line;
		int index = 0;

		while ((line = reader.readLine()) != null) {
			// planets[index++] = createPlanet(parseLine(line));
		}

		reader.close();
		return planets;
	}

	private static BufferedReader readFile() throws FileNotFoundException {
		return new BufferedReader(new FileReader(FILENAME));
	}

	private static String[] parseLine(String line) {
		return line.split(FIELD_DELIMITER);
	}

	// private static Planet createPlanet(String[] attributes) {
	// String name = attributes[0];
	// float diameter = Float.valueOf(attributes[1]);
	// float distance = Float.valueOf(attributes[2]);
	// return new Planet(name, diameter, distance);
	// }

}

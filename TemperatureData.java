package shaffer.j;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class TemperatureData {

	private static Scanner inputFile;
	static File file = new File("temperatures.dat");
	static List<Integer> tempData = new ArrayList<Integer>();
	static Map<Integer, Integer> tempMap = new HashMap<>();

	public static void main(String[] args) {
		System.out.println("Welcome to temperature analysis.");

		openFile();
		addToArray();
		createMap();
		displayTemps();
		frequencyTemps();

		System.out.println("Good bye!");
	}

	public static void openFile() {
		try {
			inputFile = new Scanner(file);
		} catch (IOException e) {
			System.out.println("Error: could not open file.");
			System.exit(0);
		}
	}

	public static void addToArray() {
		double sumOfArray = 0.0;
		// loop thru file to add data to array
		while (inputFile.hasNext()) {
			int temp = inputFile.nextInt();
			tempData.add(temp);
		}
		inputFile.close();

		for (int i = 0; i < tempData.size(); i++) {
			sumOfArray += tempData.get(i);
		}
		double average = sumOfArray / tempData.size();

		System.out.printf("%nThere are " + tempData.size() + " total temperature data values.%n%n");
		System.out.printf("The coldest temperature is: %d%n%n", Collections.min(tempData));
		System.out.printf("The warmest temperature is: %d%n%n", Collections.max(tempData));
		System.out.printf("The average temperature is: %.2f%n%n", average);
	}

	public static void createMap() {
		for (Integer data : tempData) {
			if (tempMap.containsKey(data)) {
				int count = tempMap.get(data);
				tempMap.put(data, count + 1);
			} else {
				tempMap.put(data, 1);
			}
		}
	}

	public static void displayTemps() {
		Set<Integer> keys = tempMap.keySet();
		TreeSet<Integer> sortedKeys = new TreeSet<>(keys);

		System.out.println("Frequency of each temperature:");
		System.out.printf("%nTemp\t\tValue%n=====\t\t=====%n");

		for (Integer temp : sortedKeys) {
			System.out.printf("%-10s%10s%n", temp, tempMap.get(temp));
		}
	}

	public static void frequencyTemps() {
		Map.Entry<Integer, Integer> maxValue = null;
		Map.Entry<Integer, Integer> minValue = null;

		for (Map.Entry<Integer, Integer> max : tempMap.entrySet()) {
			if (maxValue == null || max.getValue().compareTo(maxValue.getValue()) > 0) {
				maxValue = max;
			}
		}
		for (Map.Entry<Integer, Integer> min : tempMap.entrySet()) {
			if (minValue == null || min.getValue().compareTo(minValue.getValue()) < 0) {
				minValue = min;
			}
		}

		System.out.printf("%nThe most frequent temperature is: %d%n%n", maxValue.getKey());
		System.out.printf("The least frequent temperature is: %d%n%n", minValue.getKey());

	}
}

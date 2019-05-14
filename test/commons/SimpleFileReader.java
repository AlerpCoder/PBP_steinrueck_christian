package commons;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// The implementation of a file reader
// @author Eugene Yip
// DO NOT MODIFY THIS FILE
public class SimpleFileReader {

	// Function to read a file into an array list of strings, where each element
	// stores one line of the input file
	// .
	// the parameter "path" describes the file path to read from
	// .
	// it returns an array list of strings, where each element stores one line
	// of the input file
	public static ArrayList<String> readFileToStringList(String path) {
		ArrayList<String> nodesAsStrings = new ArrayList<String>();
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		try {
			fileReader = new FileReader(path);
			bufferedReader = new BufferedReader(fileReader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				nodesAsStrings.add(line);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return nodesAsStrings;
	}

}

package histogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Histogram {

	// fields
	private File file;
	private HashMap<Character, Integer> frequencyTable; // die Tabelle der
														// Buchstaben und ihrer
														// Häufigkeit
	PrintWriter printOut = null;
	FileWriter writeOut = null;
	FileWriter intOut = null;

	// constructor
	public Histogram(String fileName) {
		file = new File(fileName);
		frequencyTable = new HashMap<Character, Integer>();
		initTable();
	}

	// eine getterMethod für die HashMap -> wird später vom Unit Test benötigt
	public HashMap<Character, Integer> getFrequencyTable() {
		return frequencyTable;
	}

	// Initialisierung der Häufigkeitstabelle
	// der Ausdruck "(char) i" liefert den character an der Stelle i der ascii
	// Tabelle
	private void initTable() {
		for (int i = 65; i < 91; i++) {
			frequencyTable.put((char) i, 0);
		}
	}

	// main Methode, erstellt ein Histogramm und führt die read() aus
	public static void main(String[] args) {
		Histogram histogram = new Histogram("ambra.txt");
		histogram.start();
	}

	private void start() {
		readFromFile();
		writeStringToFile();
		writeIntegerToFile();
		writeIntToFile();
		createFile();
	}

	/*
	 * die file wird in den fileReader gegeben, jeder char wird eingelesen, wenn
	 * der char im ausgewählten bereich ist, dann wird die zahl wieder zum char
	 * gemacht -> als actualCharacter der save() übergeben
	 */
	private void readFromFile() {
		FileReader fileReader = null; // der fileReader muss außerhalb des
										// try/catch blocks initialisiert werden
		try { // gab sonst probleme
			fileReader = new FileReader(file);
			while (fileReader.ready()) { //
				int asciiValue = fileReader.read();
				if ((asciiValue > 96 && asciiValue < 123) || asciiValue > 64 && asciiValue < 91) {
					if (asciiValue > 96 && asciiValue < 123) {
						asciiValue = asciiValue - 32;
					}
					char actualCharacter = (char) asciiValue;
					save(actualCharacter);
				}
			}
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("File Not Found.");
			}
			e.printStackTrace();
		}
		try {
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(frequencyTable);
	}

	/*
	 * die save() nimmt den actualCharacter und wandelt ihn in ein Character
	 * Object des wrappers (notwendig für hashmap) der workaround zur hashmap
	 * war notwendig, weil es probleme gab beim updaten der key/value pairs der
	 * HM
	 */
	private void save(char actualCharacter) {
		Character c = new Character(actualCharacter);
		// ab hier workaround zum updaten der hashmap
		Integer value = frequencyTable.get(c);
		frequencyTable.remove(actualCharacter);
		frequencyTable.put(actualCharacter, value.intValue() + 1); // das
																	// normale
																	// ++inkrement
																	// hat hier
																	// nicht
																	// funktioniert
	}

	// Methoden für Aufgabe 2 - StringToFile, IntegerToFile, intToFIle,
	// createFile
	private void writeStringToFile() {
		try {
			printOut = new PrintWriter("stringOutput.txt");
			printOut.write("Test"); // starts the OutputStream
			printOut.close(); // closes the - " - and is necessary to complete
								// the writing process
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeIntegerToFile() {
		try {
			Integer number = new Integer(1234);
			writeOut = new FileWriter("IntegerOutput.txt");
			writeOut.write(number.toString());
			writeOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeIntToFile() {
		try {
			int i = 4321;
			intOut = new FileWriter("intOutput.txt");
			intOut.write(String.valueOf(i));
			intOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createFile() {
		try {
			File file = new File("createFile.txt");
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package histogram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

public class Histogram {

    // fields
    private File file;
    private HashMap<Character, Integer> frequencyTable;
    PrintWriter printOut = null;
    PrintWriter frequencyOut = null;
    FileWriter writeOut = null;
    FileWriter intOut = null;

    // constructor
    public Histogram(String fileName) {
        file = new File(fileName);
        frequencyTable = new HashMap<Character, Integer>();
        initTable();
    }

    // a getter method for the HashMap, will be needed for JUnit tests
    public HashMap<Character, Integer> getFrequencyTable() {
        return frequencyTable;
    }

    // initialising the frequencyTable with the chars a-z and value 0
    private void initTable() {
        for (int i = 65; i < 91; i++) {
            frequencyTable.put((char) i, 0);
        }
    }

    // the main method creates a Histogram and runs start()
    public static void main(String[] args) throws IOException {
        Histogram histogram = new Histogram("ambra.txt");
        histogram.start();
    }

    private void start() throws IOException {
        readFromFile();
        writeStringToFile();
        writeIntegerToFile();
        writeIntToFile();
        createFile();
        printFrequencyTable();
        createFrequencyFile(getFrequencyList(frequencyTable));
    }

    /*
      reads all characters from file that are in a-z
      prints out the frequencyTable and calls the createFrequencyFile()
    */
    private void readFromFile() throws IOException {
        FileReader fileReader = null;
        fileReader = new FileReader(file);
        prepareFrequencySave(fileReader);
        fileReader.close();
    }

    public void prepareFrequencySave(FileReader fileReader) throws IOException {
        while (fileReader.ready()) {
            int asciiValue = fileReader.read();
            if ((asciiValue > 96 && asciiValue < 123) || asciiValue > 64 && asciiValue < 91) {
                if (asciiValue > 96 && asciiValue < 123) {
                    asciiValue = asciiValue - 32;
                }
                char actualCharacter = (char) asciiValue;
                save(actualCharacter);
            }
        }
    }

    private void save(char actualCharacter) {
        Character c = new Character(actualCharacter);
        // ab hier workaround zum updaten der hashmap
        Integer value = frequencyTable.get(c);
        frequencyTable.put(actualCharacter, value.intValue() + 1);
        //das normale ++inkrement hat hier nicht funktioniert
    }

    // returns a String that contains the frequencyTable in Character - value pairs
    private String getFrequencyList(HashMap<Character, Integer> frequencyTable) {
        String frequencyList = "";
        Iterator it = frequencyTable.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry) it.next();
            frequencyList += (pair.getKey() + " - " + pair.getValue() + "\n");
            it.remove();
        }
        return frequencyList;
    }

    // creates a new file that contains the String it is given
    private void createFrequencyFile(String frequencyList) throws IOException {
        frequencyOut = new PrintWriter("frequencyOutput.txt");
        frequencyOut.write(frequencyList);
        frequencyOut.close();
    }

    /*
      Methoden für Aufgabe 2 - StringToFile, IntegerToFile, intToFIle,
      methods for assignment 2
     */

    private void writeStringToFile() throws IOException {
        printOut = new PrintWriter("stringOutput.txt");
        printOut.write("Test"); // starts the OutputStream
        printOut.close(); // closes the - " - and is necessary to complete
        // the writing process
    }

    private void writeIntegerToFile() throws IOException {
        Integer number = new Integer(1234);
        writeOut = new FileWriter("IntegerOutput.txt");
        writeOut.write(number.toString());
        writeOut.close();
    }

    private void writeIntToFile() throws IOException {
        int i = 4321;
        intOut = new FileWriter("intOutput.txt");
        intOut.write(String.valueOf(i));
        intOut.close();
    }

    private void createFile() throws IOException {
        File file = new File("createFile.txt");
        file.createNewFile();
    }

    private void printFrequencyTable() {
        System.out.println(frequencyTable + "\n");
        String f = getFrequencyList(frequencyTable);
        System.out.println(f);
    }
}

package edu.epam.multithreading.main;

import edu.epam.multithreading.entity.Ship;
import edu.epam.multithreading.exception.FileReaderException;
import edu.epam.multithreading.generator.PierRequestGenerator;
import edu.epam.multithreading.parser.FileParser;
import edu.epam.multithreading.reader.FileReader;

import java.util.List;

public class Main{
        private static final String FILE_PATH = "file\\data.txt";

        public static void main(String[] args) throws FileReaderException {
            FileReader reader = new FileReader();
            List<String> data = reader.readfile(FILE_PATH);
            FileParser parser = new FileParser();
            List<Ship> ships = parser.parse(data);
            PierRequestGenerator generator = new PierRequestGenerator();
            generator.generateRequests(ships);
        }
}

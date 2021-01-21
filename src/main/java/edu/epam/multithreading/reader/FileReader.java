package edu.epam.multithreading.reader;

import edu.epam.multithreading.exception.FileReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
    private static final Logger logger = LogManager.getLogger(FileReader.class);

    public List<String> readfile(String fileName) throws FileReaderException {
        List<String> stringList;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            String message;
            stringList = stream.collect(Collectors.toList());
            message = String.format("File %s was successfully read!", fileName);
            logger.info(message);
        } catch (IOException e) {
            throw new FileReaderException("Error while reading file!", e);
        }
        return stringList;
    }
}

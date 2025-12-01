import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class provides functionality for processing files in some way.
 * The FileProcessor class offers methods to read and write files.
 * <p>
 * With the FileProcessor class at our disposal, we can confidently work with files in this application.
 * It simplifies the process of reading, writing, and managing files, allowing us to focus on the actual logic of our programs.
 */

public class FileProcessor {

    private FileProcessor() {}

    static class FileReaderInteger {

        public List<Integer> read(String fileName) throws IOException {
            LinkedList<Integer> list = new LinkedList<>();
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(Integer.parseInt(line.trim()));
                }
            }
            return list;
        }
    }

    static class FileWriterInteger {

        public void write(String fileName, List<Integer> data) throws IOException {
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
                for (Integer i: data) {
                    writer.write(i.toString());
                    writer.newLine();
                }
            }
        }
    }

    static class FileReaderWords {

        public List<String> read(String fileName) throws IOException {
            LinkedList<String> list = new LinkedList<>();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                }
            }
            return list;
        }
    }

    static class FileWriterWords {

        public void write(String fileName, List<String> data) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
                for (String str : data) {
                    writer.write(str);
                    writer.newLine();
                }
            }
        }
    }

    static class FileReaderBoolean {

        public List<Boolean> read(String fileName) throws IOException {
            LinkedList<Boolean> list = new LinkedList<>();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    list.add(Boolean.parseBoolean(line.trim()));
                }
            }
            return list;
        }
    }

    static class FileWriterBoolean {

        public void write(String fileName, List<Boolean> data) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
                for (Boolean bool : data) {
                    writer.write(bool.toString());
                    writer.newLine();
                }
            }
        }
    }
}
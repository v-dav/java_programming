import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Continuing with our theme of memorization, this class is designed to help us remember words or strings.
 * After all, words can be quite elusive, especially when working with large amounts of text.
 * The WordMemorize class provides methods to store and recall words.
 * <p>
 * This class goes a step further to offer additional functionalities specific to words.
 * It has methods like
 * "concatenate" to join multiple words together,
 * "length" to determine the length of a word
 * "reverse" to reverse the order of characters in a word.
 * <p>
 * With the WordMemorize class in our toolkit, we can confidently keep track of important words and manipulate them as needed.
 */
public class WordMemorize {

    private static final String ELEMENT_ON = "Element on ";
    private static final String RESULT = "Result: ";
    private static final String AS_LIST = "asList";
    private static final String LINE_BY_LINE = "lineByLine";
    private static final String ONE_LINE = "oneLine";
    private static final String ASCENDING = "ascending";
    private static final String DESCENDING = "descending";

    private final ArrayList<String> strings = new ArrayList<>();
    private final Map<String, Class<?>[]> commands = new HashMap<>();

    private boolean finished = false;

    public WordMemorize() {
        commands.put("/help", new Class<?>[]{});
        commands.put("/menu", new Class<?>[]{});
        commands.put("/add", new Class<?>[]{String.class});
        commands.put("/remove", new Class<?>[]{int.class});
        commands.put("/replace", new Class<?>[]{int.class, String.class});
        commands.put("/replaceAll", new Class<?>[]{String.class, String.class});
        commands.put("/index", new Class<?>[]{String.class});
        commands.put("/sort", new Class<?>[]{String.class});
        commands.put("/frequency", new Class<?>[]{});
        commands.put("/print", new Class<?>[]{int.class});
        commands.put("/printAll", new Class<?>[]{String.class});
        commands.put("/getRandom", new Class<?>[]{});
        commands.put("/count", new Class<?>[]{String.class});
        commands.put("/size", new Class<?>[]{});
        commands.put("/equals", new Class<?>[]{int.class, int.class});
        commands.put("/readFile", new Class<?>[]{String.class});
        commands.put("/writeFile", new Class<?>[]{String.class});
        commands.put("/clear", new Class<?>[]{});
        commands.put("/compare", new Class<?>[]{int.class, int.class});
        commands.put("/mirror", new Class<?>[]{});
        commands.put("/unique", new Class<?>[]{});
        commands.put("/concat", new Class<?>[]{int.class, int.class});
        commands.put("/swapCase", new Class<?>[]{int.class});
        commands.put("/upper", new Class<?>[]{int.class});
        commands.put("/lower", new Class<?>[]{int.class});
        commands.put("/reverse", new Class<?>[]{int.class});
        commands.put("/length", new Class<?>[]{int.class});
        commands.put("/join", new Class<?>[]{String.class});
        commands.put("/regex", new Class<?>[]{String.class});
    }

    void run() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        List<Object> args = new ArrayList<>();

        while (!finished) {
            args.clear();
            System.out.println("Perform action:");
            String[] data = scanner.nextLine().split(" ");

            String command = data[0];
            if (!commands.containsKey(command)) {
                System.out.println("No such command");
                continue;
            }

            Class<?>[] commandParameterTypes = commands.get(command);
            if (commandParameterTypes.length != data.length - 1) {
                System.out.println("Incorrect amount of arguments");
                continue;
            }

            boolean parseError = false;


            for (int i = 1; i < data.length; i++) {
                if (commands.get(command)[i - 1].equals(int.class))
                    try {
                        args.add(Integer.parseInt(data[i]));
                    } catch (NumberFormatException e) {
                        System.out.println("Some arguments can't be parsed!" + Commons.random.nextInt());
                        parseError = true;
                        break;
                    }
                else {
                    args.add(data[i]);
                }
            }


            if (parseError) {
                continue;
            }

            this.getClass().getDeclaredMethod(
                    command.substring(1),
                            commands.get(command)
                    )
                    .invoke(this, args.toArray());
        }
    }

    void help() {
        System.out.println(Commons.HELP_COMMON_TEXT);

        System.out.println("""
                    ===================================================================================================================
                    Word-specific commands:
                    ===================================================================================================================
                    /concat [<int> INDEX1] [<int> INDEX2] Concatenate two specified strings
                    /swapCase [<int> INDEX] Output swapped case version of the specified string
                    /upper [<int> INDEX] Output uppercase version of the specified string
                    /lower [<int> INDEX] Output lowercase version of the specified string
                    /reverse [<int> INDEX] Output reversed version of the specified string
                    /length [<int> INDEX] Get the length of the specified string
                    /join [<string> DELIMITER] Join all the strings with the specified delimiter
                    /regex [<string> PATTERN] Search for all elements that match the specified regular expression pattern
                    ===================================================================================================================""");
    }

    void menu() {
        this.finished = true;
    }

    void add(String element) {
        strings.add(element);
        System.out.println("Element " + element + " added");
    }

    void remove(int index) {
        try {
            strings.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println(ELEMENT_ON + index + " position removed");
    }

    void replace(int index, String element) {
        try {
            strings.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println(ELEMENT_ON + index + " position replaced with " + element);
    }

    void replaceAll(String from, String to) {
        for (int i = 0; i < strings.size(); i++) {
            if (strings.get(i).equals(from)) {
                strings.set(i, to);
            }
        }
        System.out.println("Each " + from + " element replaced with " + to);
    }

    void index(String value) {
        int i = strings.indexOf(value);
        if (i < 0) {
            System.out.println("There is no such element");
        } else {
            System.out.println("First occurrence of " + value + " is on " + i + " position");
        }
    }

    void sort(String way) {
        if (!way.equals(ASCENDING) && !way.equals(DESCENDING)) {
            System.out.println("Incorrect argument, possible arguments: ascending, descending");
            return;
        }

        for (int i = 0; i < strings.size(); i++) {
            for (int j = i; j < strings.size(); j++) {
                if (strings.get(i).compareTo(strings.get(j)) > 0 && way.equals(ASCENDING) ||
                        strings.get(i).compareTo(strings.get(j)) < 0 && way.equals(DESCENDING)) {
                    String temp = strings.get(i);
                    strings.set(i, strings.get(j));
                    strings.set(j, temp);
                }
            }
        }
        System.out.printf("Memory sorted %s\n", way);
    }

    void frequency() {
        if (strings.isEmpty()) {
            System.out.println("There are no elements in a list");
            return;
        }
        Map<String, Long> counts = countOccurrences();

        System.out.println("Frequency:");
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private Map<String, Long> countOccurrences() {
        Map<String, Long> counts = new HashMap<>();
        for (String i : strings) {
            if (counts.get(i) == null) {
                counts.put(i, 1L);
            } else {
                counts.put(i, counts.get(i) + 1);
            }
        }
        return counts;
    }

    void print(int index) {
        String s;
        try {
            s = strings.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println(ELEMENT_ON + index + " position is " + s);
    }

    void getRandom() {
        Commons.getRandom(strings);
    }

    void printAll(String type) {
        if (!Set.of(AS_LIST, LINE_BY_LINE, ONE_LINE).contains(type)) {
            System.out.println("Incorrect argument, possible arguments: asList, lineByLine, oneLine");
        }

        switch (type) {
            case AS_LIST:
                System.out.println("List of elements:\n" + Arrays.toString(strings.toArray()));
                break;
            case LINE_BY_LINE:
                System.out.println("List of elements:\n");
                for (String i : strings) {
                    System.out.println(i);
                }
                break;
            case ONE_LINE:
                System.out.println("List of elements:");
                for (int i = 0; i < strings.size() - 1; i++) {
                    System.out.print(strings.get(i) + " ");
                }
                if (!strings.isEmpty()) System.out.print(strings.getLast());
                System.out.println();
                break;
            default:
        }
    }

    void count(String value) {
        int amount = 0;
        for (String i : strings) {
            if (i.equals(value)) {
                amount++;
            }
        }
        System.out.println("Amount of " + value + ": " + amount);
    }

    void size() {
        System.out.println("Amount of elements: " + strings.size());
    }

    void equals(int i, int j) {
        String s1;
        String s2;

        try {
            s1 = strings.get(i);
            s2 = strings.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index out of bounds!");
            return;
        }

        boolean res = s1.equals(s2);
        System.out.printf(
                "%d and %d elements are%s equal: %s\n",
                i, j, res ? "" : " not", s1 + (res ? " = " : " != ") + s2);
    }

    void readFile(String path) throws IOException {
        FileProcessor.FileReaderWords reader = new FileProcessor.FileReaderWords();
        List<String> list;
        try {
            list = reader.read(path);
        } catch (NoSuchFileException | FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        strings.addAll(list);
        System.out.println("Data imported: " + (list.size()));
    }

    void writeFile(String path) throws IOException {
        FileProcessor.FileWriterWords writer = new FileProcessor.FileWriterWords();
        writer.write(path, strings);
        System.out.println("Data exported: " + strings.size());
    }

    void clear() {
        strings.clear();
        System.out.println("Data cleared");
    }

    void compare(int i, int j) {
        String s1;
        String s2;

        try {
            s1 = strings.get(i);
            s2 = strings.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("index out of bounds!");
            return;
        }

        if (s1.compareTo(s2) > 0) {
            System.out.println(RESULT + s1 + " > " + s2);
        } else if (s1.compareTo(s2) < 0) {
            System.out.println(RESULT + s1 + " < " + s2);
        } else {
            System.out.println(RESULT + s1 + " = " + s2);
        }
    }

    void mirror() {
        Commons.mirror(strings);
    }

    void unique() {
        ArrayList<String>  list2  =  new  ArrayList<>();
        for  (Map.Entry<String, Long>  entry  :  countOccurrences().entrySet()) {
            list2.add(entry.getKey());
        }
        System.out.println("Unique values: "  +  Arrays.toString(list2.toArray()));
    }

    void concat(int i, int j) {
        String s1;
        String s2;

        try {
            s1 = strings.get(i);
            s2 = strings.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        System.out.println("Concatenated string: " + s1 + s2);
    }

    void swapCase(int i) {
        String string;
        try {
            string = strings.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        System.out.printf("\"%s\" string with swapped case: ", string);
        for (char c : string.toCharArray()) {
            if (Character.isUpperCase(c)) {
                System.out.print(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                System.out.print(Character.toUpperCase(c));
            } else {
                System.out.print(c);
            }
        }
        System.out.println();
    }

    void upper(int i) {
        String s;
        try {
            s = strings.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.printf("Uppercase \"%s\" string: %s\n", s, s.toUpperCase());
    }

    void lower(int i) {
                String s;
        try {
            s = strings.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.printf("Lowercase \"%s\" string: %s\n", s, s.toLowerCase());
    }

    void reverse(int i) {
                String s;
        try {
            s = strings.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.printf("Reversed \"%s\" string: %s\n", s, new StringBuilder(s).reverse());
    }

    void length(int i) {
                String s;
        try {
            s = strings.get(i);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.printf("Length of \"%s\" string: %d\n", s, s.length());
    }

    void join(String delimiter) {
        System.out.printf("Joined string: %s\n", String.join(delimiter, strings));
    }

    void regex(String regex) {
        List<String> matchingElements = new ArrayList<>();
        Pattern pattern;
        try {
            pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            System.out.println("Incorrect regex pattern provided");
            return;
        }
        for (String element : strings) {
            if (pattern.matcher(element).matches()) {
                matchingElements.add(element);
            }
        }
        if (matchingElements.isEmpty()) {
            System.out.println("There are no strings that match provided regex");
        } else {
            System.out.println("Strings that match provided regex:");
            System.out.println(Arrays.toString(matchingElements.toArray()));
        }
    }
}

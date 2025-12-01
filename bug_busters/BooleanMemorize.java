import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NoSuchFileException;
import java.util.*;

/**
 * It is all about memorizing Booleans. You see, regular Booleans are so forgetful!
 * They constantly change their value, and it's just too much for us to handle.
 * It probably has a magical power to store Boolean values indefinitely. You can pass a Boolean to it, and it will remember it forever.
 * <p>
 * This class must be a lifesaver for forgetful programmers like me. No more worrying about Booleans changing unexpectedly.
 * We can now rely on the trustworthy BooleanMemorize class to keep our Booleans intact. I can't wait to use it in my next project!
 */
public class BooleanMemorize {

    private static final String ELEMENT_ON = "Element on ";
    private static final String RESULT = "Result: ";
    private static final String AS_LIST = "asList";
    private static final String LINE_BY_LINE = "lineByLine";
    private static final String ONE_LINE = "oneLine";
    private static final String ASCENDING = "ascending";
    private static final String DESCENDING = "descending";

    private final ArrayList<Boolean> booleans = new ArrayList<>();
    private final Map<String, Class<?>[]> commands = new HashMap<>();

    private boolean finished = false;

    public BooleanMemorize() {
        commands.put("/help", new Class<?>[]{});
        commands.put("/menu", new Class<?>[]{});
        commands.put("/add", new Class<?>[]{Boolean.class});
        commands.put("/remove", new Class<?>[]{int.class});
        commands.put("/replace", new Class<?>[]{int.class, Boolean.class});
        commands.put("/replaceAll", new Class<?>[]{Boolean.class, Boolean.class});
        commands.put("/index", new Class<?>[]{Boolean.class});
        commands.put("/sort", new Class<?>[]{String.class});
        commands.put("/frequency", new Class<?>[]{});
        commands.put("/print", new Class<?>[]{int.class});
        commands.put("/printAll", new Class<?>[]{String.class});
        commands.put("/getRandom", new Class<?>[]{});
        commands.put("/count", new Class<?>[]{Boolean.class});
        commands.put("/size", new Class<?>[]{});
        commands.put("/equals", new Class<?>[]{int.class, int.class});
        commands.put("/readFile", new Class<?>[]{String.class});
        commands.put("/writeFile", new Class<?>[]{String.class});
        commands.put("/clear", new Class<?>[]{});
        commands.put("/compare", new Class<?>[]{int.class, int.class});
        commands.put("/mirror", new Class<?>[]{});
        commands.put("/unique", new Class<?>[]{});
        commands.put("/flip", new Class<?>[]{int.class});
        commands.put("/negateAll", new Class<?>[]{});
        commands.put("/and", new Class<?>[]{int.class, int.class});
        commands.put("/or", new Class<?>[]{int.class, int.class});
        commands.put("/logShift", new Class<?>[]{int.class});
        commands.put("/convertTo", new Class<?>[]{String.class});
        commands.put("/morse", new Class<?>[]{});
    }

    void run() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        List<Object> args = new ArrayList<>();

        while (!finished) {
            args.clear();
            System.out.println("Perform action:");
            String[] data = scanner.nextLine().split(" ");

            boolean parseError = false;
            String inputCommand = data[0];

            if (!commands.containsKey(inputCommand)) {
                System.out.println("No such command");
                continue;
            }

            Class<?>[] commandParameterTypes = commands.get(inputCommand);
            if (commandParameterTypes.length != data.length - 1) {
                System.out.println("Incorrect amount of arguments");
                continue;
            }

            for (int i = 1; i < data.length; i++) {
                if (commandParameterTypes[i - 1].equals(int.class))
                    try {
                        args.add(Integer.parseInt(data[i]));
                    } catch (NumberFormatException e) {
                        System.out.println("Some arguments can't be parsed!");
                        parseError = true;
                        break;
                    }
                else if (commandParameterTypes[i - 1].equals(Boolean.class)) {
                    if (!data[i].equals("true") && !data[i].equals("false")) {
                        System.out.println("Some arguments can't be parsed");
                        parseError = true;
                        break;
                    }
                    args.add(data[i].equals("true"));
                } else {
                    args.add(data[i]);
                }
            }

            if (parseError) {
                continue;
            }

            this
                    .getClass()
                    .getDeclaredMethod(inputCommand.substring(1), commandParameterTypes)
                    .invoke(this, args.toArray());
        }
    }

    void help() {
        System.out.println(Commons.HELP_COMMON_TEXT);

        System.out.println("""
                    ===================================================================================================================
                    Boolean-specific commands:
                    ===================================================================================================================
                    /flip [<int> INDEX] - Flip the specified boolean
                    /negateAll - Negate all the booleans in memory
                    /and [<int> INDEX1] [<int> INDEX2] - Calculate the bitwise AND of the two specified elements
                    /or [<int> INDEX1] [<int> INDEX2] - Calculate the bitwise OR of the two specified elements
                    /logShift [<int> NUM] - Perform a logical shift of elements in memory by the specified amount
                    /convertTo [string/number] - Convert the boolean(bit) sequence in memory to the specified type
                    /morse - Convert the boolean(bit) sequence to Morse code
                    ===================================================================================================================""");
    }

    void menu() {
        this.finished = true;
    }

    void add(Boolean element) {
        booleans.add(element);
        System.out.println("Element  " + element + "  added");
    }

    void remove(int index) {
        try {
            booleans.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println(ELEMENT_ON + index + " position removed");
    }

    void replace(int index, Boolean element) {
        try {
            booleans.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println("Element on " + index + " position replaced with " + element);
    }

    void replaceAll(Boolean from, Boolean to) {
        for (int i = 0; i < booleans.size(); i++) {
            if (booleans.get(i).equals(from)) {
                booleans.set(i, to);
            }
        }
        System.out.println("Each " + from + " element replaced with " + to);
    }

    void index(Boolean value) {
        int index = booleans.indexOf(value);
        if (index < 0) {
            System.out.println("There is no such element");
        } else {
            System.out.println("First occurrence of " + value + " is on " + index + " position");
        }
    }

    void sort(String way) {
        if (!way.equals(ASCENDING) && !way.equals(DESCENDING)) {
            System.out.println("Incorrect argument, possible arguments: ascending, descending");
            return;
        }

        for (int i = 0; i < booleans.size(); i++) {
            for (int j = i; j < booleans.size(); j++) {
                if (booleans.get(i) && !booleans.get(j) && way.equals(ASCENDING) ||
                        !booleans.get(i) && booleans.get(j) && way.equals(DESCENDING)) {
                    Boolean temp = booleans.get(i);
                    booleans.set(i, booleans.get(j));
                    booleans.set(j, temp);
                }
            }
        }
        System.out.printf("Memory sorted %s\n", way);
    }

    void frequency() {
        if (booleans.isEmpty()) {
            System.out.println("There are no elements in a list");
            return;
        }

        System.out.println("Frequency:");
        for (Map.Entry<Boolean, Long> entry : countBooleanOccurrences().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    void print(int index) {
        Boolean b;
        try {
             b = booleans.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println("Element on " + index + " position is " + b);
    }

    void getRandom() {
        Commons.getRandom(booleans);
    }

    void printAll(String type) {
        if (!Set.of(AS_LIST, LINE_BY_LINE, ONE_LINE).contains(type)) {
            System.out.println("Incorrect argument, possible arguments: asList, lineByLine, oneLine");
        }

        switch (type) {
            case AS_LIST:
                System.out.println("List of elements:\n" +
                        Arrays.toString(booleans.toArray()));
                break;
            case LINE_BY_LINE:
                System.out.println("List of elements:\n");
                for (Boolean i : booleans) {
                    System.out.println(i);
                }
                break;
            case ONE_LINE:
                System.out.println("List of elements:");
                for (int i = 0; i < booleans.size() - 1; i++) {
                    System.out.print(booleans.get(i) + " ");
                }
                if (!booleans.isEmpty())
                    System.out.print(booleans.getLast());
                System.out.println();
                break;
            default:
                break;
        }
    }

    void count(Boolean value) {
        int amount = 0;
        for (Boolean i : booleans) {
            if (Objects.equals(i, value)) {
                amount++;
            }
        }
        System.out.println("Amount of " + value + ": " + amount);
    }

    void size() {
        System.out.println("Amount of elements: " + booleans.size());
    }

    void equals(int i, int j) {
        Boolean b1;
        Boolean b2;

        try {
            b1 = booleans.get(i);
            b2 = booleans.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        boolean res = b1.equals(b2);
        System.out.printf("%d and %d elements are%s equal: %s\n",
                i, j, res ? "" : " not", b1 + (res ? " = " : " != ") + b2);
    }

    void readFile(String path) throws IOException {
        FileProcessor.FileReaderBoolean reader = new FileProcessor.FileReaderBoolean();
        List<Boolean> list;
        try {
            list = reader.read(path);
        } catch (NoSuchFileException | FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        booleans.addAll(list);
        System.out.println("Data imported: " + (list.size()));
    }

    void writeFile(String path) throws IOException {
        FileProcessor.FileWriterBoolean writer = new FileProcessor.FileWriterBoolean();
        writer.write(path, booleans);
        System.out.println("Data exported: " + booleans.size());
    }

    void clear() {
        booleans.clear();
        System.out.println("Data cleared");
    }

    void compare(int i, int j) {
        Boolean b1;
        Boolean b2;

        try {
            b1 = booleans.get(i);
            b2 = booleans.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }


        if (Boolean.TRUE.equals(b1) && !b2) {
            System.out.println(RESULT + b1 + " > " + b2);
        } else if (!b1 && Boolean.TRUE.equals(b2)) {
            System.out.println("Result: " + b1 + " < " + b2);
        } else {
            System.out.println("Result: " + b1 + " = " + b2);
        }
    }

    void mirror() {
        Commons.mirror(booleans);
    }

    void unique() {
        ArrayList<Boolean> list2 = new ArrayList<>();
        for (Map.Entry<Boolean, Long> entry : countBooleanOccurrences().entrySet()) {
            list2.add(entry.getKey());
        }
        System.out.println("Unique values: " + Arrays.toString(list2.toArray()));
    }

    private Map<Boolean, Long> countBooleanOccurrences() {
        Map<Boolean, Long> counts = new HashMap<>();
        for (Boolean b : booleans) {
            counts.merge(b, 1L, Long::sum);  // More efficient than manual check
        }
        return counts;
    }

    void flip(int index) {
        try {
            booleans.set(index, !booleans.get(index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println("Element on " + index + " position flipped");
    }

    void negateAll() {
        booleans.replaceAll(e -> !e);
        System.out.println("All elements negated");
    }

    void and(int i, int j) {
        boolean a;
        boolean b;

        try {
            a = booleans.get(i);
            b = booleans.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        boolean res = a && b;
        System.out.printf("Operation performed: (%b && %b) is %b\n", a, b, res);
    }

    void or(int i, int j) {
        boolean a;
        boolean b;

        try {
            a = booleans.get(i);
            b = booleans.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        boolean res = a || b;
        System.out.printf("Operation performed: (%b || %b) is %b\n", a, b, res);
    }

    void logShift(int n) {
        int outputValue = n;
        int size = booleans.size();

        if (size == 0) {
            return;
        }
        n %= size;
        if (n < 0) {
            n += size;
        }
        for (int i = 0; i < n; i++) {
            Boolean last = booleans.get(size - 1);
            for (int j = size - 1; j > 0; j--) {
                booleans.set(j, booleans.get(j - 1));
            }
            booleans.set(0, last);
        }
        System.out.println("Elements shifted by " + outputValue);
    }

    void convertTo(String type) {
        if (booleans.isEmpty()) {
            System.out.println("No data memorized");
            return;
        }
        StringBuilder binary = new StringBuilder();
        for (boolean b : booleans) {
            if (b) {
                binary.append("1");
            } else {
                binary.append("0");
            }
        }
        switch (type.toLowerCase()) {
            case "number":
                System.out.println("Converted: " + Long.parseLong(binary.toString(), 2));
                break;
            case "string":
                int byteSize = Byte.SIZE;
                StringBuilder sb = new StringBuilder();
                if (binary.length() % byteSize != 0) {
                    System.out.println("Amount of elements is not divisible by 8, so the last " + binary.length() % byteSize + " of " +
                            "them will be ignored");
                }
                for (int i = 0; i < binary.length(); i += byteSize) {
                    String segment = binary.substring(i, Math.min(i + byteSize, binary.length()));
                    int asciiValue = Integer.parseInt(segment, 2);
                    sb.append((char) asciiValue);
                }
                String asciiSequence = sb.toString();
                System.out.println("Converted: " + asciiSequence);
                break;
            default:
                System.out.println("Incorrect argument, possible arguments: string, number");
                break;
        }
    }

    void morse() {
        if (booleans.isEmpty()) {
            System.out.println("No data memorized");
            return;
        }

        StringBuilder morseCode = new StringBuilder("Morse code: ");
        for (boolean b : booleans) {
            if (b) {
                morseCode.append(".");
            } else {
                morseCode.append("_");
            }
        }
        System.out.println(morseCode);
    }
}

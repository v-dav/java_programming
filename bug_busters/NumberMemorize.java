package memorizingtool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.NoSuchFileException;
import java.util.*;

/**
 * Ah, the class NumberMemorize! Well, if we're following the same line of thinking, it is all about helping us remember numbers.
 * Because, let's be honest, numbers can be quite slippery and elusive sometimes.
 * <p>
 * But that's not all! It has some additional features tailored specifically for numbers like:
 * "increment" to increase the stored value by a certain amount,
 * "decrement" to decrease it, and maybe even "multiply" and "divide" to perform basic arithmetic operations.
 * <p>
 * With NumberMemorize at our disposal, we won't have to worry about forgetting or losing track of important numerical values.
 * It's like having a virtual assistant dedicated solely to keeping our numbers safe and accessible.
 */
public class NumberMemorize {

    private static final String ELEMENT_ON = "Element on ";
    private static final String CALCULATION_PERFORMED = "Calculation performed:";
    public static final String RESULT = "Result: ";
    private static final String AS_LIST = "asList";
    private static final String LINE_BY_LINE = "lineByLine";
    private static final String ONE_LINE = "oneLine";
    private static final String ASCENDING = "ascending";
    private static final String DESCENDING = "descending";

    private final ArrayList<Integer> integers = new ArrayList<>();
    private final Map<String, Class<?>[]> commands = new HashMap<>();
    private boolean finished = false;

    public NumberMemorize() {
        commands.put("/help", new Class<?>[]{});
        commands.put("/menu", new Class<?>[]{});
        commands.put("/add", new Class<?>[]{int.class});
        commands.put("/remove", new Class<?>[]{int.class});
        commands.put("/replace", new Class<?>[]{int.class, int.class});
        commands.put("/replaceAll", new Class<?>[]{int.class, int.class});
        commands.put("/index", new Class<?>[]{int.class});
        commands.put("/sort", new Class<?>[]{String.class});
        commands.put("/frequency", new Class<?>[]{});
        commands.put("/print", new Class<?>[]{int.class});
        commands.put("/printAll", new Class<?>[]{String.class});
        commands.put("/getRandom", new Class<?>[]{});
        commands.put("/count", new Class<?>[]{int.class});
        commands.put("/size", new Class<?>[]{});
        commands.put("/equals", new Class<?>[]{int.class, int.class});
        commands.put("/readFile", new Class<?>[]{String.class});
        commands.put("/writeFile", new Class<?>[]{String.class});
        commands.put("/clear", new Class<?>[]{});
        commands.put("/compare", new Class<?>[]{int.class, int.class});
        commands.put("/mirror", new Class<?>[]{});
        commands.put("/unique", new Class<?>[]{});
        commands.put("/sum", new Class<?>[]{int.class, int.class});
        commands.put("/subtract", new Class<?>[]{int.class, int.class});
        commands.put("/multiply", new Class<?>[]{int.class, int.class});
        commands.put("/divide", new Class<?>[]{int.class, int.class});
        commands.put("/pow", new Class<?>[]{int.class, int.class});
        commands.put("/factorial", new Class<?>[]{int.class});
        commands.put("/sumAll", new Class<?>[]{});
        commands.put("/average", new Class<?>[]{});
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
                    Number-specific commands:
                    ===================================================================================================================
                    /sum [<int> INDEX1] [<int> INDEX2] - Calculate the sum of the two specified elements
                    /subtract [<int> INDEX1] [<int> INDEX2] - Calculate the difference between the two specified elements
                    /multiply [<int> INDEX1] [<int> INDEX2] - Calculate the product of the two specified elements
                    /divide [<int> INDEX1] [<int> INDEX2] - Calculate the division of the two specified elements
                    /pow [<int> INDEX1] [<int> INDEX2] - Calculate the power of the specified element to the specified exponent element
                    /factorial [<int> INDEX] - Calculate the factorial of the specified element
                    /sumAll - Calculate the sum of all elements
                    /average - Calculate the average of all elements
                    ===================================================================================================================""");
    }

    void menu() {
        this.finished = true;
    }

    void add(int element) {
        integers.add(element);
        System.out.println("Element " + element + " added");
    }

    void remove(int index) {
        try {
            integers.remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println(ELEMENT_ON + index + " position removed");
    }

    void replace(int index, int element) {
        try {
            integers.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println("Element on " + index + " position replaced with " + element);
    }

    void replaceAll(int from, int to) {
        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i).equals(from)) {
                integers.set(i, to);
            }
        }
        System.out.println("Each " + from + " element replaced with " + to);
    }

    void index(int value) {
        int i = integers.indexOf(value);
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

        for (int i = 0; i < integers.size(); i++) {
            for (int j = i; j < integers.size(); j++) {
                if (integers.get(i) > integers.get(j) && way.equals(ASCENDING)
                        || integers.get(i) < integers.get(j) && way.equals(DESCENDING)) {
                    int temp = integers.get(i);
                    integers.set(i, integers.get(j));
                    integers.set(j, temp);
                }
            }
        }
        System.out.printf("Memory sorted %s\n", way);
    }

    void frequency() {
        if (integers.isEmpty()) {
            System.out.println("There are no elements in a list");
            return;
        }
        Map<Integer, Long> counts = countOccurrences();
        System.out.println("Frequency:");
        for (Map.Entry<Integer, Long> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private Map<Integer, Long> countOccurrences() {
        Map<Integer, Long> counts = new HashMap<>();
        for (int i : integers) {
            if (counts.get(i) == null) {
                counts.put(i, 1L);
            } else {
                counts.put(i, counts.get(i) + 1);
            }
        }
        return counts;
    }

    void print(int index) {
        Integer i;
        try {
            i = integers.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }
        System.out.println("Element on " + index + " position is " + i);
    }

    void getRandom() {
        Commons.getRandom(integers);
    }

    void printAll(String type) {
        if (!Set.of(AS_LIST, LINE_BY_LINE, ONE_LINE).contains(type)) {
            System.out.println("Incorrect argument, possible arguments: asList, lineByLine, oneLine");
        }

        switch (type) {
            case AS_LIST:
                System.out.println("List of elements:\n" +
                        Arrays.toString(integers.toArray()));
                break;
            case LINE_BY_LINE:
                System.out.println("List of elements:\n");
                for (int i : integers) {
                    System.out.println(i);
                }
                break;
            case ONE_LINE:
                System.out.println("List of elements:");
                for (int i = 0; i < integers.size() - 1; i++) {
                    System.out.print(integers.get(i) + " ");
                }
                if (!integers.isEmpty())
                    System.out.print(integers.getLast());
                System.out.println();
                break;
            default:
                break;
        }
    }

    void count(int value) {
        int amount = 0;
        for (int i : integers) {
            if (i == value) {
                amount++;
            }
        }
        System.out.println("Amount of " + value + ": " + amount);
    }

    void size() {
        System.out.println("Amount of elements: " + integers.size());
    }

    void equals(int i, int j) {
        Integer i1;
        Integer i2;

        try {
            i1 = integers.get(i);
            i2 = integers.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        boolean res = i1.equals(i2);
        System.out.printf(
                "%d and %d elements are%s equal: %s\n",
                i, j, res ? "" : " not", i1 + (res ? " = " : " != ") + i2);
    }

    void readFile(String path) throws IOException {
        FileProcessor.FileReaderInteger readerThread = new FileProcessor.FileReaderInteger();
        List<Integer> list;
        try {
            list = readerThread.read(path);
        } catch (NoSuchFileException | FileNotFoundException e) {
            System.out.println("File not found!");
            return;
        }
        integers.addAll(list);
        System.out.println("Data imported: " + (list.size()));
    }

    void writeFile(String path) throws IOException {
        FileProcessor.FileWriterInteger writer = new FileProcessor.FileWriterInteger();
        writer.write(path, integers);
        System.out.println("Data exported: " + integers.size());
    }

    void clear() {
        integers.clear();
        System.out.println("Data cleared");
    }

    void compare(int i, int j) {
        Integer i1;
        Integer i2;

        try {
            i1 = integers.get(i);
            i2 = integers.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }


        if (i1 > i2) {
            System.out.println(RESULT + i1 + " > " + i2);
        } else if (i1 < i2) {
            System.out.println(RESULT + i1 + " < " + i2);
        } else {
            System.out.println(RESULT + i1 + " = " + i2);
        }
    }

    void mirror() {
        Commons.mirror(integers);
    }

    void unique() {
        ArrayList<Integer> list2 = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : countOccurrences().entrySet()) {
            list2.add(entry.getKey());
        }

        System.out.println("Unique values: " + Arrays.toString(list2.toArray()));
    }

    void sum(int i, int j) {
        long i1;
        long i2;

        try {
            i1 = integers.get(i);
            i2 = integers.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        long res = i1 + i2;
        System.out.printf(CALCULATION_PERFORMED + " %d + %d = %d\n", i1, i2, res);
    }

    void subtract(int i, int j) {
        long i1;
        long i2;

        try {
            i1 = integers.get(i);
            i2 = integers.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        long res = i1 - i2;
        System.out.printf(CALCULATION_PERFORMED + " %d - %d = %d\n",i1, i2, res);
    }

    void multiply(int i, int j) {
        long i1;
        long i2;

        try {
            i1 = integers.get(i);
            i2 = integers.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        long res = i1 * i2;
        System.out.printf(CALCULATION_PERFORMED + " %d * %d = %d\n", i1, i2, res);
    }

    void divide(int i, int j) {
        long i1;
        long i2;

        try {
            i1 = integers.get(i);
            i2 = integers.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        if (i2 == 0) {
            System.out.println("Division by zero");
            return;
        }

        double res = (double) i1 / i2;
        System.out.printf(CALCULATION_PERFORMED + " %d / %d = %.6f\n", i1, i2, res);
    }

    void pow(int i, int j) {
        long a;
        long b;

        try {
            a = integers.get(i);
            b = integers.get(j);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        if (b < 0) {
            // Handle negative exponent: a^(-b) = 1 / a^b
            long positiveRes = 1;
            for (int k = 0; k < -b; k++) {
                positiveRes *= a;
            }
            double res = 1.0 / positiveRes;
            System.out.printf(CALCULATION_PERFORMED + " %d ^ %d = %.6f\n", a, b, res);
        } else {
            // Handle positive exponent
            long res = 1;
            for (int k = 0; k < b; k++) {
                res *= a;
            }
            System.out.printf(CALCULATION_PERFORMED + " %d ^ %d = %d\n", a, b, res);
        }
    }

    void factorial(int index) {
        Integer integer;
        try {
            integer = integers.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index out of bounds!");
            return;
        }

        if (integer < 0) {
            System.out.println("undefined");
            return;
        }
        long res = 1;
        int i = 1;

        do {
            res = res * (i++);
        } while (i <= integer);
        System.out.printf(CALCULATION_PERFORMED + " %d! = %d\n", integer, res);
    }

    void sumAll() {
        long sum = 0;
        for (long i : integers) {
            sum += i;
        }
        System.out.println("Sum of all elements: " + sum);
    }

    void average() {
        long sum = 0;
        for (long i : integers) {
            sum += i;
        }
        System.out.printf("Average of all elements: %.6f\n", (double) sum / integers.size());
    }
}

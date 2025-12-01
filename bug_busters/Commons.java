package memorizingtool;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Commons {

    public static final Random random = new Random();

    private Commons() {
        // private constructor
    }

    public static final String HELP_COMMON_TEXT = """
                    ===================================================================================================================
                    Usage: COMMAND [<TYPE> PARAMETERS]
                    ===================================================================================================================
                    General commands:
                    ===================================================================================================================
                    /help - Display this help message
                    /menu - Return to the menu
                    
                    /add [<T> ELEMENT] - Add the specified element to the list
                    /remove [<int> INDEX] - Remove the element at the specified index from the list
                    /replace [<int> INDEX] [<T> ELEMENT] - Replace the element at specified index with the new one
                    /replaceAll [<T> OLD] [<T> NEW] - Replace all occurrences of specified element with the new one
                    
                    /index [<T> ELEMENT] - Get the index of the first specified element in the list
                    /sort [ascending/descending] - Sort the list in ascending or descending order
                    /frequency - The frequency count of each element in the list
                    /print [<int> INDEX] - Print the element at the specified index in the list
                    /printAll [asList/lineByLine/oneLine] - Print all elements in the list in specified format
                    /getRandom - Get a random element from the list
                    /count [<T> ELEMENT] - Count the number of occurrences of the specified element in the list
                    /size - Get the number of elements in the list
                    /equals [<int> INDEX1] [<int> INDEX2] - Check if two elements are equal
                    /clear - Remove all elements from the list
                    /compare [<int> INDEX1] [<int> INDEX2] Compare elements at the specified indices in the list
                    /mirror - Mirror elements' positions in list
                    /unique - Unique elements in the list
                    /readFile [<string> FILENAME] - Import data from the specified file and add it to the list
                    /writeFile [<string> FILENAME] - Export the list data to the specified file""";

    public static void getRandom(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("There are no elements memorized");
            return;
        }
        int index = random.nextInt(list.size());
        Object b = list.get(index);
        System.out.println("Random element: " + b);
    }


    public static <T> void mirror(List<T> list) {
        Collections.reverse(list);
        System.out.println("Data reversed");
    }
}

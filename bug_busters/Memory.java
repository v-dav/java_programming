// Chapter 1
// Once upon a time in a small village nestled between rolling hills, there lived...
// a curious young girl named Lily. Lily had a heart full of...
// adventure and a mind hungry for knowledge. Every day, she would wander through the...
// village, observing the world around her and asking questions that often left the villagers perplexed...

//Chapter 2
//One sunny morning, as Lily strolled by the village square, she noticed a...
//peculiar shimmering object hidden behind a cluster of bushes. Intrigued, she cautiously...
//approached the bushes and discovered a beautifully crafted silver key. The key...
//gleamed in the sunlight, as if beckoning her to uncover its secrets.

//Chapter 3
//With excitement coursing through her veins, Lily decided to embark on a quest to find the lock...

//Chapter 4
//that the mysterious key belonged to. She spent days poring over books in the village library, searching...
//for any mention of a hidden treasure or a forgotten secret that might hold the key to her discovery.
//One evening, while deep in her research, Lily stumbled upon an ancient map hidden...
//within the pages of an old book. The map depicted a hidden cave at the summit of the tallest hill, rumored...
//to hold the key to unlocking unimaginable wonders. The key in Lily's...
//possession seemed to match the one shown on the map.
//With the map as her guide, Lily set out on an arduous journey up the treacherous hill, navigating through...
//dense forests and rocky terrain. After days of perseverance, she finally reached the summit and stood before...
//the entrance of the hidden cave. With a deep breath, she inserted the silver key into the lock, and with...

//a satisfying click, the heavy doors slowly creaked open, revealing a dazzling...
//chamber filled with sparkling jewels and ancient artifacts.
//And so, Lily's unwavering curiosity and determination led her to a treasure...
//trove of knowledge and beauty. From that day forward, she became known as the village's greatest...
//explorer, sharing her discoveries and inspiring others to pursue their own adventures.

package memorizingtool;

import java.util.Scanner;

/**
 * This class is all about joining or combining the functionalities of the previously mentioned classes
 * (BooleanMemorize, NumberMemorize, and WordMemorize) into one comprehensive memory management system.
 * <p>
 * With the Memory class, we can now have a unified approach to memory management,
 * allowing us to store and manipulate different types of data seamlessly
 */
public class Memory {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(
                    "Welcome to Data Memory!\n" +
                            "Possible actions:\n" +
                            "1. Memorize booleans\n" +
                            "2. Memorize numbers\n" +
                            "3. Memorize words\n" +
                            "0. Quit"
            );

            String choice = scanner.next();
            switch (choice) {
                case "1":
                    new BooleanMemorize().run();
                    System.gc();
                    break;
                case "2":
                    new NumberMemorize().run();
                    System.gc();
                    break;
                case "3":
                    new WordMemorize().run();
                    System.gc();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Incorrect command");
                    break;
            }
        }
    }
}
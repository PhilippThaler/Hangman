import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Hangman
 * 
 * @author Philipp Thaler
 * 
 *         Little Hangman game I made. It uses the word file from
 *         https://github.com/Xethron/Hangman/blob/master/words.txt to generate
 *         random words.
 */
public class Hangman {

    public static void main(String[] args) {
        File file = new File("words.txt");
        ArrayList<String> words = new ArrayList<>();
        Scanner scanner;

        // Reading the words from the file
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine().toUpperCase());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Word file not found!");
            System.out.println("Closing the program...");
            System.exit(1);
        }

        boolean playing = true;
        while (playing) {// Grabbing a random word
            String word = words.get(ThreadLocalRandom.current().nextInt(words.size()));

            // char array for comparing the user input against the word
            char[] guess = new char[word.length()];
            Arrays.fill(guess, '_');

            int wrongGuesses = 0;
            scanner = new Scanner(System.in);
            boolean isRight;
            char input;
            ArrayList<Character> usedChars = new ArrayList<>();

            while (wrongGuesses < Drawing.stages.length && !word.equals(new String(guess))) {
                isRight = false;
                System.out.print("Word: ");
                System.out.println(guess);

                input = input();

                // Checks for already used letters
                while (usedChars.contains(input)) {
                    System.out.println("You already used this letter!");
                    System.out.print("Word: ");
                    System.out.println(guess);
                    input = input();
                }
                usedChars.add(input);

                // If the guess was right, make the letters visible
                for (int i = 0; i < word.length(); i++) {
                    if (input == word.charAt(i)) {
                        guess[i] = input;
                        isRight = true;
                    }
                }

                // Prints the hanged man
                System.out.println(Drawing.stages[wrongGuesses]);

                if (!isRight) {
                    System.out.println("Wrong guess!");
                    wrongGuesses++;
                }
            }
            System.out.println(
                    (word.equals(new String(guess))) ? word + "\nYou won!" : "You lost, the word was: " + word);

            System.out.println("Do you want to play again? [Y/N]");
            if (scanner.next().toUpperCase().charAt(0) == 'Y') {
                playing = true;
            } else {
                playing = false;
            }
        }
    }

    // Method for handling input
    public static char input() {
        Scanner scanner = new Scanner(System.in);

        String input = "";

        while (input.equals("")) {
            input = scanner.nextLine().toUpperCase();
        }

        return input.charAt(0);
    }
}

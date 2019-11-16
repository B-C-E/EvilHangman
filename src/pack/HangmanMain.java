package pack;

// Class HangmanMain is the driver program for the Hangman program.  It reads a
// dictionary of words to be used during the game and then plays a game with
// the user.  This is a cheating version of hangman that delays picking a word
// to keep its options open.  You can change the setting for DEBUG to see
// how many options are still left on each turn.

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HangmanMain
{
    public static final String DICTIONARY_FILE = "dictionary.txt";
    public static final boolean DEBUG = false; // show words left

    public static void main(String[] args) throws FileNotFoundException
    {
        System.out.println("Welcome to the cs 145 hangman game!");
        System.out.println();

        System.out.println("Please place a file named \"dictionary.txt\" into your downloads folder");


        System.out.println("Has the file been placed?");
        while (GetInput.getYN() != 'y')
        {
            System.out.println("Please place the file.");
            System.out.println("Has the file been placed?");
        }
        // open the dictionary file and read dictionary into an ArrayList
        Path location = Paths.get(System.getProperty("user.home") + "/Downloads/dictionary.txt");
        Scanner input = new Scanner(location.toFile());
        List<String> dictionary = new ArrayList<String>();
        while (input.hasNext())
        {
            dictionary.add(input.next().toLowerCase());
        }
        List<String> dictionary2 = Collections.unmodifiableList(dictionary);


        //Game loop
        do
        {
            // set basic parameters
            System.out.print("What length word do you want to use? ");
            int length = GetInput.getRangeInt(0, 29);
            System.out.print("How many wrong answers allowed? ");
            int max = GetInput.getRangeInt(0, 26);
            System.out.println();

            // set up the HangmanManager and start the game
            HangmanManager hangman = new HangmanManager(dictionary2, length, max);
            if (hangman.words().isEmpty())
            {
                System.out.println("No words of that length in the dictionary.");
            } else
            {
                playGame(hangman);
                showResults(hangman);
            }

            System.out.println();
            System.out.println("Start a new game?");

        } while (GetInput.getYN() == 'y');
        //end of game loop
    }//end of main

    // Plays one game with the user
    public static void playGame(HangmanManager hangman)
    {
        while (hangman.guessesLeft() > 0 && hangman.pattern().contains("-"))
        {
            System.out.println("guesses : " + hangman.guessesLeft());
            if (DEBUG)
            {
                System.out.println(hangman.words().size() + " words left: "
                        + hangman.words());
            }
            System.out.println("guessed : " + hangman.guesses());
            System.out.println("current : " + hangman.pattern());
            System.out.print("Your guess? ");
            char ch = GetInput.getLetter();
            if (hangman.guesses().contains(ch))
            {
                System.out.println("You already guessed that.");
            }
            else
            {
                int count = hangman.record(ch);
                if (count == 0)
                {
                    System.out.println("Sorry, there are no " + ch + "'s.");
                } else if (count == 1)
                {
                    System.out.println("Yes, there is one " + ch +".");
                } else
                {
                    System.out.println("Yes, there are " + count + " " + ch
                            + "'s.");
                }
            }
            System.out.println();
        }
    }

    // reports the results of the game, including showing the answer
    public static void showResults(HangmanManager hangman)
    {
        // if the game is over, the answer is the first word in the list
        // of words, so we use an iterator to get it
        String answer = hangman.words().iterator().next();
        System.out.println("answer = " + answer);
        if (hangman.guessesLeft() >= 0)
        {
            System.out.println("You beat me!");
        } else
        {
            System.out.println("Sorry, you lose...");
        }
    }
}
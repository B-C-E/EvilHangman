package pack;

import java.util.*;

public class HangmanManager implements ManagerInterface
{

    private int length;
    private int max;//max guesses
    private List<String> dictionary;
    private Set<String> words;//possible words
    private SortedSet<Character> guesses;//abcegio, etc
    private String family; //used to store the letter arrangement family, ie -aa-

    //constructor
    public HangmanManager(List<String> dictionary,int length, int max)
    {
        this.dictionary = dictionary;
        words = new HashSet<>();

        //set up words
        for (String word:dictionary)
        {
            //if it is the right length
            if (word.length() == length)
            {
                words.add(word);
            }
        }

        //set up default family
        family = "";
        for (int i =0; i < length; i++)
        {
            family+= "-";
        }

        // set up other fields
        guesses = new TreeSet<Character>();
        this.length = length;
        this.max = max;
    }//end of constructor

    //list of all possible words that could work for the game
    public Set<String> words()
    {
    return words;
    }//end of words

    //returns number of guesses left
    public int guessesLeft()
    {
    return max;
    }//end of guessesLeft

    //returns a sorted set of all characters that have been guessed
    public SortedSet<Character> guesses()
    {
        return guesses;
    }//end of guesses

    //returns, for example : "a - - l e" for apple, if a, l, and e have been guessed
    public String pattern()
    {
        //if the length is 1, just return "-" or whatever
        if (length == 1)
        {
            return family;
        }

        //add the first character
        String toReturn = family.charAt(0) +"";

        //for all remaining characters, add a space, then add it
        for (int i = 1; i < family.length(); i++)
        {
            toReturn+= " " + family.charAt(i);
        }

        return toReturn;
    }//emd of pattern

    public int record(char guess)
    {
        guesses.add(guess);
        Map<String,HashSet<String>> families = new HashMap<String,HashSet<String>>();

        //make a list of families
            //storage variables
            String family = "";
            char currentChar;

        //for each possible word
        for (String word: words)
        {

            //check each word in words, and generate a family for it based on where it contains guess
            for (int i = 0; i < length; i++)
            {
                currentChar=word.charAt(i);
                if (currentChar == guess)
                {
                    family += guess;
                }
                else
                {
                    family+= '-';
                }

            }

            //if families has its family as a key, add the word to that list
            if (families.containsKey(family))
            {
                families.get(family).add(word);
            }
            else //otherwise, create a new key with just that word in its list
            {
                families.put(family,new HashSet<String>());
                families.get(family).add(word);
            }

            //reset family
            family = "";
        }//end of for each word in words

        //find largest family
        String largestFamily = "";
        int numbInLarge = 0;

        for (String fam: families.keySet())
        {

            if (families.get(fam).size() > numbInLarge)
            {
                largestFamily = fam;
                numbInLarge = families.get(fam).size();
            }
        }


        //set up all the fields so that they are good
        words = families.get(largestFamily);
        max--;

        //return the number of times char showed up in the largest family AND setup the "family" field for this class
        int count = 0;
        for (int i = 0; i < largestFamily.length(); i++ )
        {
            if (largestFamily.charAt(i) == guess)
            {
                count++;
            }
            if (largestFamily.charAt(i) != '-')
            {
                //if we need to change the last one
                if (i == largestFamily.length()-1)
                {
                    this.family = this.family.substring(0,i)+guess;
                }
                else if (i== 0)//if we need to change the first one
                {
                    this.family=guess + this.family.substring(1);
                }
                    else//if we need to change any in the middle
                {
                    this.family = this.family.substring(0, i) + guess + this.family.substring(i + 1);
                }
            }
        }

        return count;
        //done!
    }//end of record
}

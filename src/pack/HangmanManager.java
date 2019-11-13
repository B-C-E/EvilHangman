package pack;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

public class HangmanManager implements ManagerInterface
{
    private List<String> dictionary;
    private int length;
    private int max;//max guesses
    private Set<String> words;//possible words
    private SortedSet<Character> guesses;

    //constructor
    public HangmanManager(List<String> dictionary,int length, int max)
    {
        this.dictionary = dictionary;
        this.length = length;
        this.max = max;
    }//end of constructor

    //list of all possible words that could work for the game
    public Set<String> words()
    {
    return words;
    }//end of words

    public int guessesLeft()
    {
    return max;
    }//end of guessesLeft

    public SortedSet<Character> guesses()
    {
        return guesses;
    }//end of guesses

    public String pattern()
    {
        String toReturn = "";
        for (int i = 0; i < length; i++)
        {

        }

        return toReturn;
    }//emd of pattern

    public int record(char guess)
    {

    }//end of record
}

package pack;

import java.util.Set;
import java.util.SortedSet;

//An interface to make sure that I meet
//the project specifications
public interface ManagerInterface
{
    Set<String> words();
    int guessesLeft();
    SortedSet<Character> guesses();
    String pattern();
    int record(char guess);
}

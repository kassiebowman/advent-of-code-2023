package aoc;

import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Day 3: Gear Ratios
 *
 * @see <a href="https://adventofcode.com/2023/day/3">AOC 2023 Day 3</a>
 */
public class Day03
{
    protected static final CharMatcher DIGIT_CHAR_MATCHER = CharMatcher.inRange('0', '9');
    protected static final CharMatcher PERIOD_CHAR_MATCHER = CharMatcher.is('.');
    protected static final CharMatcher SYMBOL_CHAR_MATCHER = PERIOD_CHAR_MATCHER.or(DIGIT_CHAR_MATCHER).negate();

    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);

        long sum = 0;

        final int numLines = lines.size();
        for (int i = 0; i < numLines; i++)
        {
            String line = lines.get(i);
            int lineLength = line.length();

            // Search through the line to find the numbers one at a time
            int currentIndex = 0;
            while (currentIndex < lineLength)
            {
                final int numberStartIndex = DIGIT_CHAR_MATCHER.indexIn(line, currentIndex);
                if (numberStartIndex != -1)
                {
                    // Found the start of a number sequence; keep going until the found digit is not a number or end of
                    // line is reached
                    int numberEndIndex = numberStartIndex + 1;
                    while (numberEndIndex < lineLength && DIGIT_CHAR_MATCHER.matches(line.charAt(numberEndIndex)))
                    {
                        numberEndIndex++;
                    }

                    boolean isPartNumber = false;

                    // Once a number is found, check all of adjacent characters to see if any are a symbol (not '.' and
                    // not a digit).
                    // We'll start with the current line because that's easiest:
                    if (numberStartIndex > 0 && !PERIOD_CHAR_MATCHER.matches(line.charAt(numberStartIndex - 1)))
                    {
                        isPartNumber = true;
                    } else if (numberEndIndex < lineLength && !PERIOD_CHAR_MATCHER.matches(line.charAt(numberEndIndex)))
                    {
                        isPartNumber = true;
                    }

                    int searchStartIndex = numberStartIndex > 0 ? numberStartIndex - 1 : numberStartIndex;
                    int searchEndIndex = numberEndIndex < lineLength ? numberEndIndex + 1 : numberEndIndex;

                    // Next check the previous line
                    if (!isPartNumber && i > 0)
                    {
                        String previousLine = lines.get(i - 1);
                        isPartNumber = SYMBOL_CHAR_MATCHER.matchesAnyOf(previousLine.subSequence(searchStartIndex, searchEndIndex));
                    }

                    if (!isPartNumber && i < numLines - 1)
                    {
                        String nextLine = lines.get(i + 1);
                        isPartNumber = SYMBOL_CHAR_MATCHER.matchesAnyOf(nextLine.subSequence(searchStartIndex, searchEndIndex));
                    }

                    // Extract the number from the string and add it to the sum
                    if (isPartNumber)
                    {
                        sum += Integer.parseInt(line.substring(numberStartIndex, numberEndIndex));
                    }

                    // Resume the search after this number
                    currentIndex = numberEndIndex;
                } else {
                    break;
                }
            }
        }

        return sum;
    }
}

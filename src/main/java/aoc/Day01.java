package aoc;

import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 1: Trebuchet?!
 *
 * @see <a href="https://adventofcode.com/2023/day/1">AOC 2023 Day 1</a>
 */
public class Day01
{
    private static final Map<String, Integer> numberStrings = new HashMap<>();

    static
    {
        numberStrings.put("one", 1);
        numberStrings.put("two", 2);
        numberStrings.put("three", 3);
        numberStrings.put("four", 4);
        numberStrings.put("five", 5);
        numberStrings.put("six", 6);
        numberStrings.put("seven", 7);
        numberStrings.put("eight", 8);
        numberStrings.put("nine", 9);
    }

    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);

        long sum = 0;
        for (String line : lines)
        {
            // Part 1 solution derived from: https://www.baeldung.com/java-find-numbers-in-string
            final int firstDigitIndex = CharMatcher.inRange('0', '9').indexIn(line);
            final int lastDigitIndex = CharMatcher.inRange('0', '9').lastIndexIn(line);

            int firstNumStringIndex = line.length();
            int lastNumStringIndex = -1;
            int firstNumStringValue = -1;
            int lastNumStringValue = -1;
            if (!part1)
            {
                for (Map.Entry<String, Integer> entry : numberStrings.entrySet())
                {
                    final String numberString = entry.getKey();
                    final Integer numberStringValue = entry.getValue();
                    int index = line.indexOf(numberString);
                    if (index != -1 && index < firstNumStringIndex)
                    {
                        firstNumStringIndex = index;
                        firstNumStringValue = numberStringValue;
                    }

                    index = line.lastIndexOf(numberString);
                    int adjustedIndex = index + numberString.length() - 1;
                    if (index != -1 && adjustedIndex > lastNumStringIndex)
                    {
                        lastNumStringIndex = adjustedIndex;
                        lastNumStringValue = numberStringValue;
                    }
                }
            }

            int firstNum;
            int lastNum;
            if (part1)
            {
                firstNum = line.charAt(firstDigitIndex) - '0';
                lastNum = line.charAt(lastDigitIndex) - '0';
            } else {
                firstNum = (firstDigitIndex != -1 && firstDigitIndex < firstNumStringIndex) ?
                        line.charAt(firstDigitIndex) - '0' : firstNumStringValue;
                lastNum = (lastDigitIndex != -1 && lastDigitIndex > lastNumStringIndex) ?
                        line.charAt(lastDigitIndex) - '0' : lastNumStringValue;
            }

            sum += firstNum * 10 + lastNum;
        }

        return sum;
    }
}
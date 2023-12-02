package aoc;

import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Day 1: Trebuchet?!
 *
 * @see <a href="https://adventofcode.com/2023/day/1">AOC 2023 Day 1</a>
 */
public class Day01
{
    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);

        long sum = 0;
        for (String line : lines)
        {
            // Solution derived from: https://www.baeldung.com/java-find-numbers-in-string
            final int firstNumIndex = CharMatcher.inRange('0', '9').indexIn(line);
            final int lastNumIndex = CharMatcher.inRange('0', '9').lastIndexIn(line);

            int firstNum = line.charAt(firstNumIndex) - '0';
            int lastNum = line.charAt(lastNumIndex) - '0';
            sum += firstNum * 10 + lastNum;
        }

        return sum;
    }
}

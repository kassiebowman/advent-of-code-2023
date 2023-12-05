package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Day 4: Scratchcards
 *
 * @see <a href="https://adventofcode.com/2023/day/4">AOC 2023 Day 4</a>
 */
public class Day04
{
    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);

        int sum = 0;

        // Each line represents a card
        for (String line : lines)
        {
            final int colonIndex = line.indexOf(':');
            final int separatorIndex = line.indexOf('|');
            final List<String> winningNumbers = List.of(line.substring(colonIndex + 2, separatorIndex - 1).trim().split("\s+"));
            final List<String> myNumbers = List.of(line.substring(separatorIndex + 2).trim().split("\s+"));

            // Find intersection of the lists
            // Reference: https://www.baeldung.com/java-lists-intersection
            final long numMatches = winningNumbers.stream()
                    .distinct() // Doesn't hurt to exclude duplicates
                    .filter(myNumbers::contains)
                    .count();

            sum += numMatches > 0 ? Math.pow(2, numMatches - 1) : 0;
        }

        return sum;
    }
}

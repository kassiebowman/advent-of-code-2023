package aoc;

import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 3: Gear Ratios
 *
 * @see <a href="https://adventofcode.com/2023/day/3">AOC 2023 Day 3</a>
 */
public class Day03
{
    protected static final CharMatcher DIGIT_CHAR_MATCHER = CharMatcher.inRange('0', '9');
    protected static final CharMatcher PERIOD_CHAR_MATCHER = CharMatcher.is('.');
    protected static final CharMatcher GEAR_CHAR_MATCHER = CharMatcher.is('*');
    protected static final CharMatcher SYMBOL_CHAR_MATCHER = PERIOD_CHAR_MATCHER.or(DIGIT_CHAR_MATCHER).negate();

    // For part 2, probably the most efficient solution would be to find the gears, narrow it down to those with two
    // adjacent numbers, and then calculate the sum of the "gear ratios". But, that means I would have to do a lot of
    // rework, so instead, I'm going to keep the logic from part 1 and just keep track of gears as I find them.
    private static final Map<Gear, List<Integer>> gearToPartNumberMap = new HashMap<>();

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
                    Gear adjacentGear = null;

                    // Once a number is found, check all of adjacent characters to see if any are a symbol (not '.' and
                    // not a digit).
                    // We'll start with the current line because that's easiest:
                    if (numberStartIndex > 0)
                    {
                        final char charToCheck = line.charAt(numberStartIndex - 1);
                        if (!PERIOD_CHAR_MATCHER.matches(charToCheck))
                        {
                            isPartNumber = true;
                            if (GEAR_CHAR_MATCHER.matches(charToCheck))
                            {
                                adjacentGear = new Gear(numberStartIndex - 1, i);
                            }
                        }
                    }

                    if (!isPartNumber && numberEndIndex < lineLength)
                    {
                        final char charToCheck = line.charAt(numberEndIndex);
                        if (!PERIOD_CHAR_MATCHER.matches(charToCheck))
                        {
                            isPartNumber = true;
                            if (GEAR_CHAR_MATCHER.matches(charToCheck))
                            {
                                adjacentGear = new Gear(numberEndIndex, i);
                            }
                        }
                    }

                    int searchStartIndex = numberStartIndex > 0 ? numberStartIndex - 1 : numberStartIndex;
                    int searchEndIndex = numberEndIndex < lineLength ? numberEndIndex + 1 : numberEndIndex;

                    // Next check the previous line
                    if (!isPartNumber && i > 0)
                    {
                        String previousLine = lines.get(i - 1);
                        final CharSequence sequence = previousLine.subSequence(searchStartIndex, searchEndIndex);
                        isPartNumber = SYMBOL_CHAR_MATCHER.matchesAnyOf(sequence);

                        if (isPartNumber)
                        {
                            final int gearIndexInSequence = GEAR_CHAR_MATCHER.indexIn(sequence);
                            if (gearIndexInSequence != -1)
                            {
                                adjacentGear = new Gear(searchStartIndex + gearIndexInSequence, i - 1);
                            }
                        }
                    }

                    if (!isPartNumber && i < numLines - 1)
                    {
                        String nextLine = lines.get(i + 1);
                        final CharSequence sequence = nextLine.subSequence(searchStartIndex, searchEndIndex);
                        isPartNumber = SYMBOL_CHAR_MATCHER.matchesAnyOf(sequence);

                        if (isPartNumber)
                        {
                            final int gearIndexInSequence = GEAR_CHAR_MATCHER.indexIn(sequence);
                            if (gearIndexInSequence != -1)
                            {
                                adjacentGear = new Gear(searchStartIndex + gearIndexInSequence, i + 1);
                            }
                        }
                    }

                    // Extract the number from the string and add it to the sum
                    if (isPartNumber)
                    {
                        final int value = Integer.parseInt(line.substring(numberStartIndex, numberEndIndex));
                        sum += value;

                        if (adjacentGear != null)
                        {
                            gearToPartNumberMap.computeIfAbsent(adjacentGear, k -> new ArrayList<>()).add(value);
                        }
                    }

                    // Resume the search after this number
                    currentIndex = numberEndIndex;
                } else
                {
                    break;
                }
            }
        }

        if (!part1)
        {
            long gearRatioSum = 0;

            // For part 2, instead of returning the sum of part numbers, we return the sum of the gear ratios
            for (List<Integer> partNumbers : gearToPartNumberMap.values())
            {
                if (partNumbers.size() != 2) continue;

                gearRatioSum += (long) partNumbers.get(0) * partNumbers.get(1);
            }

            return gearRatioSum;
        }

        return sum;
    }

    /**
     * Record to represent a "gear" in the grid.
     *
     * @param x The index of the gear symbol within a row (which is a String)
     * @param y The index of the line containing the gear symbol
     */
    private record Gear(int x, int y)
    {
    }
}

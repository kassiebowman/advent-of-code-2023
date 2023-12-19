package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 9: Mirage Maintenance
 *
 * @see <a href="https://adventofcode.com/2023/day/9">AOC 2023 Day 9</a>
 */
public class Day09
{
    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);
        long sum = 0;

        // Each line is a sequence of measurement values, separated by a space
        for (String line : lines)
        {
            final List<Long> values = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).boxed().toList();
            sum += getNextValue(values, part1);
        }

        return sum;
    }

    /**
     * Gets the next or previous value for a list of provided values by extrapolating forwards or backwards.
     * @param values The list of values
     * @param part1 {@code true} to get the next value after the end of the list; {@code false} to get the value before
     *                          the start of the list.
     * @return The next or previous value for the sequence.
     */
    private Long getNextValue(List<Long> values, boolean part1)
    {
        boolean allDiffs0 = true;
        List<Long> differences = new ArrayList<>();
        for (int i = 1; i < values.size(); i++)
        {
            final long diff = values.get(i) - values.get(i - 1);
            differences.add(diff);
            if (diff != 0) allDiffs0 = false;
        }

        final Long lastValue = values.get(values.size() - 1);
        if (allDiffs0) return lastValue;

        final Long nextDiff = getNextValue(differences, part1);
        return part1 ? lastValue + nextDiff : values.get(0) - nextDiff;
    }
}

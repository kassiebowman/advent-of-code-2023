package aoc;

import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Day 6: Wait For It
 *
 * @see <a href="https://adventofcode.com/2023/day/6">AOC 2023 Day 6</a>
 */
public class Day06
{
    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);

        List<Long> times;
        List<Long> distances;
        if (part1)
        {
            // Line 0 is the times, separated by whitespace after a label of "Time:"
            times = Arrays.stream(lines.get(0).substring(5).trim().split("\s+")).mapToLong(Long::valueOf).boxed().toList();

            // Line 1 is the distances, separated by whitespace after a label of "Distance:"
            distances = Arrays.stream(lines.get(1).substring(9).trim().split("\s+")).mapToLong(Long::valueOf).boxed().toList();
        } else {
            // Line 0 is the time, ignoring any whitespace, after a label of "Time:"
            // It is stored in a list to keep the same logic from part 1
            times = List.of(Long.valueOf(CharMatcher.inRange('0', '9').retainFrom(lines.get(0))));

            // Line 1 is the distance, ignoring any whitespace, after a label of "Distance:"
            // It is stored in a list to keep the same logic from part 1
            distances = List.of(Long.valueOf(CharMatcher.inRange('0', '9').retainFrom(lines.get(1))));
        }

        long product = 1;

        for (int i = 0; i < times.size(); i++)
        {
            long time = times.get(i);
            long distance = distances.get(i);

            int waysToWin = 0;

            for (int j = 1; j < time; j++)
            {
                if (j * (time - j) > distance) waysToWin++;
            }

            product *= waysToWin;
        }

        return product;
    }
}

package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 5: If You Give A Seed A Fertilizer
 *
 * @see <a href="https://adventofcode.com/2023/day/5">AOC 2023 Day 5</a>
 */
public class Day05
{
    private long[] seeds;
    private List<List<MapEntry>> maps = new ArrayList<>();

    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        parseInput(Utils.getInput(fileName));

        long lowestValue = Integer.MAX_VALUE;
        for (long seed : seeds)
        {
            long mappedValue = seed;
            for (List<MapEntry> map : maps)
            {
                // Check if the current value is in any of the mapped ranges. If not, it remains the same
                for (MapEntry mapEntry : map)
                {
                    final long source = mapEntry.source;
                    if (mappedValue >= source && mappedValue < source + mapEntry.length)
                    {
                        // Source value is in range so map it and break out
                        long offset = mappedValue - source;
                        mappedValue = mapEntry.destination + offset;
                        break;
                    }
                }
            }

            if (mappedValue < lowestValue) lowestValue = mappedValue;
        }

        return lowestValue;
    }

    /**
     * Parse the lines of the input file to initialize the values for {@link #seeds} and {@link #maps}.
     *
     * @param lines The lines in the input file
     */
    private void parseInput(List<String> lines)
    {
        // First line is seeds
        final String seedsString = lines.get(0).split(": ")[1];
        seeds = Arrays.stream(seedsString.split(" ")).mapToLong(Long::parseLong).toArray();

        List<MapEntry> currentMap = null;

        // Remainder of file is the maps
        for (int i = 1; i < lines.size(); i++)
        {
            final String line = lines.get(i);
            if (line.isEmpty())
            {
                currentMap = new ArrayList<>();
                maps.add(currentMap);
            } else if (!line.contains(":"))
            {
                final String[] values = line.split(" ");
                final long destination = Long.parseLong(values[0]);
                final long source = Long.parseLong(values[1]);
                final long length = Long.parseLong(values[2]);
                currentMap.add(new MapEntry(destination, source, length));
            }
        }
    }

    private record MapEntry(long destination, long source, long length)
    {
    }
}

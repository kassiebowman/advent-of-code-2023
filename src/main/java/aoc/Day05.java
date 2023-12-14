package aoc;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

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
    // To simplify processing for part 2, this list was refactored to always be a range of seeds. For part 1, it is
    // just a singleton range.
    private List<Range<Long>> seedRanges;
    private final List<List<MapEntry>> maps = new ArrayList<>();

    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        parseInput(Utils.getInput(fileName), part1);

        long lowestValue = Integer.MAX_VALUE;
        for (Range<Long> seedRange : seedRanges)
        {
            // Iterate over the values in each seed range. This is necessary for part 2 instead of creating a list with
            // all the seeds to avoid running out of memory.
            for (Long seed : ContiguousSet.create(seedRange, DiscreteDomain.longs()))
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
        }

        return lowestValue;
    }

    /**
     * Parse the lines of the input file to initialize the values for {@link #seedRanges} and {@link #maps}.
     *
     * @param lines The lines in the input file
     * @param part1 {@code true} if this is for part one where the seeds line is a list of individual seeds;
     *              {@code false} if the seeds line is pairs of seed ranges
     */
    private void parseInput(List<String> lines, boolean part1)
    {
        // First line is seeds
        final String seedsString = lines.get(0).split(": ")[1];
        final List<Long> seedValues = Arrays.stream(seedsString.split(" ")).mapToLong(Long::parseLong).boxed().toList();

        if (part1)
        {
            seedRanges = seedValues.stream().map(sv -> Range.closed(sv, sv)).toList();
        } else
        {
            seedRanges = new ArrayList<>();
            for (int i = 0; i < seedValues.size(); i += 2)
            {
                final Long startValue = seedValues.get(i);
                final Long length = seedValues.get(i + 1);
                seedRanges.add(Range.closedOpen(startValue, startValue + length));
            }
        }

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

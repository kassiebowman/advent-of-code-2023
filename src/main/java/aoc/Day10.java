package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Day 10: Pipe Maze
 *
 * @see <a href="https://adventofcode.com/2023/day/10">AOC 2023 Day 10</a>
 */
public class Day10
{
    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);
        int maxX = lines.get(0).length() - 1;
        int maxY = lines.size() - 1;

        List<PipeSegment> loop = new ArrayList<>();
        PipeSegment start = null;

        for (int i = 0; i < lines.size(); i++)
        {
            final String line = lines.get(i);
            final int index = line.indexOf('S');
            if (index != -1)
            {
                start = new PipeSegment(index, i, 'S', '\0');
                loop.add(start);
                break;
            }
        }

        // First find a pipe connected to S
        boolean loopStarted = false;
        if (start.y != 0)
        {
            // Try north
            final char northChar = lines.get(start.y - 1).charAt(start.x);
            if (northChar == '|' || northChar == '7' || northChar == 'F')
            {
                loop.add(new PipeSegment(start.x, start.y - 1, northChar, 'S'));
                loopStarted = true;
            }
        }

        if (!loopStarted && start.y != maxY)
        {
            // Try south
            final char southChar = lines.get(start.y + 1).charAt(start.x);
            if (southChar == '|' || southChar == 'L' || southChar == 'J')
            {
                loop.add(new PipeSegment(start.x, start.y + 1, southChar, 'N'));
                loopStarted = true;
            }
        }

        if (!loopStarted && start.x != maxX)
        {
            // Try east
            final char eastChar = lines.get(start.y).charAt(start.x + 1);
            if (eastChar == '-' || eastChar == '7' || eastChar == 'J')
            {
                loop.add(new PipeSegment(start.x + 1, start.y, eastChar, 'W'));
                loopStarted = true;
            }
        }

        // Note: it will never get here since there need to be at least two connections to S...
        if (!loopStarted && start.x != 0)
        {
            // Try west
            final char westChar = lines.get(start.y).charAt(start.x - 1);
            if (westChar == '-' || westChar == 'L' || westChar == 'F')
            {
                loop.add(new PipeSegment(start.x - 1, start.y, westChar, 'E'));
                loopStarted = true;
            }
        }

        // Start with S and work around the loop until arriving back at S
        int i = 1;
        boolean loopClosed = false;
        do
        {
            final PipeSegment pipeSegment = loop.get(i++);
            final int currentX = pipeSegment.x;
            final int currentY = pipeSegment.y;
            final char entryDirection = pipeSegment.entryDirection;
            int newX;
            int newY;
            char newEntryDirection;
            char travelDirection = 'N';
            switch (pipeSegment.pipeSymbol)
            {
                case '|':
                    travelDirection = entryDirection == 'N' ? 'S' : 'N';
                    break;
                case '-':
                    travelDirection = entryDirection == 'E' ? 'W' : 'E';
                    break;
                case 'L':
                    travelDirection = entryDirection == 'N' ? 'E' : 'N';
                    break;
                case 'J':
                    travelDirection = entryDirection == 'N' ? 'W' : 'N';
                    break;
                case '7':
                    travelDirection = entryDirection == 'S' ? 'W' : 'S';
                    break;
                case 'F':
                    travelDirection = entryDirection == 'S' ? 'E' : 'S';
                    break;
            }

            if (travelDirection == 'S')
            {
                newX = currentX;
                newY = currentY + 1;
                newEntryDirection = 'N';
            } else if (travelDirection == 'N')
            {
                newX = currentX;
                newY = currentY - 1;
                newEntryDirection = 'S';
            } else if (travelDirection == 'E')
            {
                newX = currentX + 1;
                newY = currentY;
                newEntryDirection = 'W';
            } else
            { // W
                newX = currentX - 1;
                newY = currentY;
                newEntryDirection = 'E';
            }

            final char newPipeChar = lines.get(newY).charAt(newX);
            if (newPipeChar == 'S') {
                loopClosed = true;
            } else
            {
                loop.add(new PipeSegment(newX, newY, newPipeChar, newEntryDirection));
            }
        } while (!loopClosed);

        return loop.size() / 2;
    }

//    private enum Pipe
//    {
//        Vertical('|'),
//        Horizontal('-'),
//
//    }

    /**
     * Record to represent a location in the grid
     *
     * @param x              The column index
     * @param y              The row index
     * @param pipeSymbol     The character representing the pipe at this location
     * @param entryDirection The direction from which the pipe was entered as a cardinal coordinate, e.g. if the pipe
     *                       segment was entered from above, it was entered from the north, represented by 'N'
     */
    private record PipeSegment(int x, int y, char pipeSymbol, char entryDirection)
    {
    }
}

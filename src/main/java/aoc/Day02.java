package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Day 2: Cube Conundrum
 *
 * @see <a href="https://adventofcode.com/2023/day/2">AOC 2023 Day 2</a>
 */
public class Day02
{
    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);

        int sum = 0;

        // Each line represents a game
        for (String line : lines)
        {
            // First get the game id, from the format "Game ##: "
            final String[] parts = line.split(": ");
            final Game game = new Game(Integer.parseInt(parts[0].substring(5)));

            // Next iterate over the sets in the game, split by a semicolon
            for (String setString : parts[1].split("; "))
            {
                final String[] cubeStrings = setString.split(", ");

                int numRed = 0;
                int numBlue = 0;
                int numGreen = 0;

                // A cube string is formatted as "## <color>"
                for (String cubeString : cubeStrings)
                {
                    final String[] cubeStringParts = cubeString.split(" ");
                    int value = Integer.parseInt(cubeStringParts[0]);
                    String colorString = cubeStringParts[1];
                    switch (colorString)
                    {
                        case "red" -> numRed = value;
                        case "blue" -> numBlue = value;
                        case "green" -> numGreen = value;
                    }
                }

                game.addSet(numRed, numBlue, numGreen);
            }

//            System.out.println(game);
//            System.out.println(game.isPossible(12, 14, 13));
            if (part1)
            {
                if (game.isPossible(12, 14, 13)) sum += game.id;
            } else {
                sum += game.getPower();
            }
        }

        return sum;
    }

    private static class Game {
        public final int id;
        private final List<Integer> reds = new ArrayList<>();
        private final List<Integer> blues = new ArrayList<>();
        private final List<Integer> greens = new ArrayList<>();

        public Game(int id)
        {
            this.id = id;
        }

        /**
         * Add a set of selected cubes to the game.
         *
         * @param numRed Number of red cubes selected
         * @param numBlue Number of blue cubes selected
         * @param numGreen Number of green cubes selected
         */
        public void addSet(int numRed, int numBlue, int numGreen)
        {
            reds.add(numRed);
            blues.add(numBlue);
            greens.add(numGreen);
        }

        /**
         * Determine if the game is possible given the max limits on the number of each cube.
         *
         * @param maxRed The maximum number of red cubes
         * @param maxBlue The maximum number of blue cubes
         * @param maxGreen The maximum number of green cubes
         * @return {@code true} if the game is possible
         */
        public boolean isPossible(int maxRed, int maxBlue, int maxGreen)
        {
            if (reds.stream().anyMatch(r -> r > maxRed)) return false;
            if (blues.stream().anyMatch(b -> b > maxBlue)) return false;
            return greens.stream().noneMatch(g -> g > maxGreen);
        }

        /**
         * @return The power of the game is product of the minimum number of red, blue, and green cubes required for the
         * game to be possible.
         */
        public int getPower()
        {
            return Collections.max(reds) * Collections.max(blues) * Collections.max(greens);
        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();
            sb.append("Game ").append(id).append(": ");

            for (int i = 0; i < reds.size(); i++)
            {
                if (i != 0) sb.append("; ");

                Integer value = reds.get(i);
                if (value > 0) sb.append(value).append(" red ");
                value = blues.get(i);
                if (value > 0) sb.append(value).append(" blue ");
                value = greens.get(i);
                if (value > 0) sb.append(value).append(" green ");
            }

            return sb.toString();
        }
    }
}

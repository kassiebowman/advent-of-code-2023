package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day07}.
 */
class Day07Test
{
    @ParameterizedTest
    @CsvSource({
            "07-control.txt, true, 6440",
            "07-data.txt, true, 249726565",
            "07-control.txt, false, 5905",
            "07-data.txt, false, 251135960",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day07().execute(resourceName, part1)).isEqualTo(value);
    }
}
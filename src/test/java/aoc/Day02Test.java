package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day02}.
 */
class Day02Test
{
    @ParameterizedTest
    @CsvSource({
            "02-control.txt, true, 8",
            "02-data.txt, true, 2720",
            "02-control.txt, false, 2286",
            "02-data.txt, false, 71535",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day02().execute(resourceName, part1)).isEqualTo(value);
    }
}
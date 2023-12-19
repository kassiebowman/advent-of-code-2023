package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day09}.
 */
class Day09Test
{
    @ParameterizedTest
    @CsvSource({
            "09-control.txt, true, 114",
            "09-data.txt, true, 2075724761",
            "09-control.txt, false, 2",
            "09-data.txt, false, 1072",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day09().execute(resourceName, part1)).isEqualTo(value);
    }
}
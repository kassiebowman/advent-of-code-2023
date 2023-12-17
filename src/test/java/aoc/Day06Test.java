package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day06}.
 */
class Day06Test
{
    @ParameterizedTest
    @CsvSource({
            "06-control.txt, true, 288",
            "06-data.txt, true, 3316275",
            "06-control.txt, false, 71503",
            "06-data.txt, false, 27102791",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day06().execute(resourceName, part1)).isEqualTo(value);
    }
}
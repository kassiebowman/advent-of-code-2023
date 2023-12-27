package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day10}.
 */
class Day10Test
{
    @ParameterizedTest
    @CsvSource({
            "10-control.txt, true, 4",
            "10-control-2.txt, true, 8",
            "10-data.txt, true, 6800",
//            "10-control.txt, false, 1924",
//            "10-data.txt, false, 9020",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day10().execute(resourceName, part1)).isEqualTo(value);
    }
}
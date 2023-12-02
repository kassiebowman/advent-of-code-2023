package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day01}.
 */
class Day01Test
{
    @ParameterizedTest
    @CsvSource({
            "01-control.txt, true, 4512",
//            "01-data.txt, true, 63552",
//            "01-control.txt, false, 1924",
//            "01-data.txt, false, 9020",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day01().execute(resourceName, part1)).isEqualTo(value);
    }
}
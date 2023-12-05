package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day04}.
 */
class Day04Test
{
    @ParameterizedTest
    @CsvSource({
            "04-control.txt, true, 13",
            "04-data.txt, true, 25340",
//            "04-control.txt, false, 1924",
//            "04-data.txt, false, 9020",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day04().execute(resourceName, part1)).isEqualTo(value);
    }
}
package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day05}.
 */
class Day05Test
{
    @ParameterizedTest
    @CsvSource({
            "05-control.txt, true, 35",
            "05-data.txt, true, 403695602",
            "05-control.txt, false, 46",
            "05-data.txt, false, 219529182",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day05().execute(resourceName, part1)).isEqualTo(value);
    }
}
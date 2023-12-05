package aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link Day03}.
 */
class Day03Test
{
    @ParameterizedTest
    @CsvSource({
            "03-control.txt, true, 4361",
            "03-data.txt, true, 526404",
            "03-control.txt, false, 467835",
            "03-data.txt, false, 84399773",
    })
    void testExecute(String resourceName, boolean part1, long value) throws URISyntaxException, IOException
    {
        assertThat(new Day03().execute(resourceName, part1)).isEqualTo(value);
    }
}
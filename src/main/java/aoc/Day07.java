package aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Day 07: Camel Cards
 *
 * @see <a href="https://adventofcode.com/2023/day/7">AOC 2023 Day 7</a>
 */
public class Day07
{
    private final List<Hand> hands = new ArrayList<>();

    long execute(String fileName, boolean part1) throws URISyntaxException, IOException
    {
        final List<String> lines = Utils.getInput(fileName);

        // Each line is a hand and a bid, separated by a space
        for (String line : lines)
        {
            final String[] parts = line.split(" ");
            final String cards = parts[0];
            final int bid = Integer.parseInt(parts[1]);
            final Type type = determineType(cards, part1);
            hands.add(new Hand(cards, type, bid));
        }

        hands.sort((hand1, hand2) -> {
            // First compare by hand type
            int value = hand1.type.compareTo(hand2.type);
            if (value == 0)
            {
                // If the hand type is the same, compare cards in order
                int i = 0;
                String cards1 = hand1.cards;
                String cards2 = hand2.cards;
                while (value == 0 && i < 5)
                {
                    final Card card1 = Card.fromCharacter(cards1.charAt(i));
                    final Card card2 = Card.fromCharacter(cards2.charAt(i));

                    if (part1)
                    {
                        value = card1.compareTo(card2);
                    } else
                    {
                        value = Integer.compare(card1.part2Rank, card2.part2Rank);
                    }

                    i++;
                }
            }

            return value;
        });

        long winnings = 0;
        // Now that the hands are sorted, calculate the winnings. The hand in index 0 has a rank of N where N is the
        // total number of hands, and the winnings is the bid times the rank.
        final int numHands = hands.size();
        for (int i = 0; i < numHands; i++)
        {
            long rank = numHands - i;
            winnings += rank * hands.get(i).bid;
        }

        return winnings;
    }

    /**
     * Determine the type of hand by counting the unique instances of cards.
     *
     * @param cards The card hand as a String with 5 characters
     * @param part1 {@code true} if this if part 1; {@code false} if this is part 2
     * @return The type of hand
     */
    private Type determineType(String cards, boolean part1)
    {
        Map<Character, Integer> cardCounts = new HashMap<>();

        for (int i = 0; i < cards.length(); i++)
        {
            final char card = cards.charAt(i);
            cardCounts.merge(card, 1, Integer::sum);
        }

        // For part 2, convert jokers to whatever card has the highest count
        if (!part1)
        {
            final Integer jokers = cardCounts.remove('J');

            if (jokers != null)
            {
                if (cardCounts.isEmpty())
                {
                    // If all the cards were jokers, put them back and move on since there is no other card to convert them to
                    cardCounts.put('J', jokers);
                } else
                {
                    // Convert the jokers to the card that has the highest count
                    final Optional<Map.Entry<Character, Integer>> maxEntry = cardCounts.entrySet().stream().max(Map.Entry.comparingByValue());
                    maxEntry.ifPresent(entry -> cardCounts.merge(entry.getKey(), jokers, Integer::sum));
                }
            }
        }

        final int uniqueCards = cardCounts.size();

        return switch (uniqueCards)
        {
            case 1 -> Type.FiveOfAKind;
            case 2 ->
            {
                final Integer maxNumCards = cardCounts.values().stream().max(Long::compare).orElse(0);
                yield maxNumCards == 4 ? Type.FourOfAKind : Type.FullHouse;
            }
            case 3 ->
            {
                final Integer maxNumCards = cardCounts.values().stream().max(Long::compare).orElse(0);
                yield maxNumCards == 3 ? Type.ThreeOfAKind : Type.TwoPair;
            }
            case 4 -> Type.OnePair;
            default -> Type.HighCard;
        };
    }

    private enum Card
    {
        A('A', 0),
        K('K', 1),
        Q('Q', 2),
        J('J', 12),
        T('T', 3),
        NINE('9', 4),
        EIGHT('8', 5),
        SEVEN('7', 6),
        SIX('6', 7),
        FIVE('5', 8),
        FOUR('4', 9),
        THREE('3', 10),
        TWO('2', 11);

        private final char cardCharacter;
        private final int part2Rank;
        private static final Map<Character, Card> map = new HashMap<>();

        static
        {
            for (Card card : values())
            {
                map.put(card.cardCharacter, card);
            }
        }

        Card(char cardCharacter, int part2Rank)
        {
            this.cardCharacter = cardCharacter;
            this.part2Rank = part2Rank;
        }

        static Card fromCharacter(char cardCharacter)
        {
            return map.get(cardCharacter);
        }
    }

    /**
     * An enum representing the type of card hands.
     */
    private enum Type
    {
        FiveOfAKind,
        FourOfAKind,
        FullHouse,
        ThreeOfAKind,
        TwoPair,
        OnePair,
        HighCard
    }

    /**
     * A record to represent a card hand, including its type and the bid amount.
     *
     * @param cards The card hand as a string of 5 cards
     * @param type  The type of card hand
     * @param bid   The bid associated with the hand
     */
    private record Hand(String cards, Type type, int bid)
    {
    }
}

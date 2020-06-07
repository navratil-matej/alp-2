package eleven.backend;

import eleven.common.Ruleset;

public class RegularRuleset implements Ruleset<RegularValues, RegularSymbols, RegularCard>
{
	private final RegularValues K = RegularValues.KING;
	private final RegularValues Q = RegularValues.QUEEN;
	private final RegularValues J = RegularValues.JACK;

	@Override
	public int cardsOnBoard()
	{
		return 9;
	}

	@Override
	public boolean areMatch(RegularCard... cards)
	{
		boolean sum, kqj;
		sum = cards.length == 2 &&pointsFor(cards[0]) + pointsFor(cards[1]) == 11;
		kqj = cards.length == 3;
		kqj = kqj && cards[0].value() == K || cards[1].value() == K || cards[2].value() == K;
		kqj = kqj && cards[0].value() == Q || cards[1].value() == Q || cards[2].value() == Q;
		kqj = kqj && cards[0].value() == J || cards[1].value() == J || cards[2].value() == J;
		return sum || kqj;
	}

	@Override
	public int pointsFor(RegularCard card)
	{
		final int ord = card.value().ordinal() + 1;
		return ord <= 10 ? ord : 0;
	}

}

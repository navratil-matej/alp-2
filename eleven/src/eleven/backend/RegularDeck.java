package eleven.backend;

import eleven.common.Deck;
import eleven.common.Shuffler;

public class RegularDeck implements Deck<RegularValues, RegularSymbols, RegularCard>
{
	public static final int CARDS_IN_DECK = 52;
	
	RegularCard[] cards;
	int pointer;
	
	private RegularDeck(int decks)
	{
		cards = new RegularCard[decks * CARDS_IN_DECK];
		for (RegularValues v : RegularValues.values())
		{
			for (RegularSymbols s : RegularSymbols.values())
			{
				for (int i = 0; i < decks; i++)
				{
					cards[pointer] = new RegularCard(v, s);
					pointer++;
				}
			}
		}
		pointer--;
	}
	
	public static RegularDeck sorted()
	{
		return sortedOf(1);
	}
	
	public static RegularDeck sortedOf(int nDecks)
	{
		return new RegularDeck(1);
	}
	
	public static RegularDeck shuffled()
	{
		return shuffledOf(1);
	}
	
	public static RegularDeck shuffledOf(int nDecks)
	{
		return new RegularDeck(1);
	}
	
	public void shuffle(Shuffler<RegularCard> shuffler)
	{
		shuffler.shuffle(cards);
	}

	@Override
	public int remaining()
	{
		return pointer + 1;
	}

	@Override
	public RegularCard take()
	{
		RegularCard c = cards[pointer];
		pointer--;
		return c;
	}
}

package blackjack;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BlackjackTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void cardToStringTest() {
		Card c = new Card(Rank.ACE, Suit.SPADES);
		assertEquals("ACE OF SPADES", c.toString());
		
	}
	
	@Test
	public void deckSetupTest() {
		Deck d = new Deck();
		
		
	}

}

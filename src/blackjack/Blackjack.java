package blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Blackjack {
	
	private static final int initialBalance = 1000;		// starting balance for every player
	private static final int dealDelay = 500;			// for dramatic dealing effect
	private static final int numDecks = 1;				// number of decks in the shoe
	private Deck cards;
	private List<HumanPlayer> players;
	private Dealer dealer;
	
	/**
	 * Blackjack constructor
	 * 
	 * To play, create an instance of Blackjack
	 * 		and call play()
	 */
	public Blackjack() {
		cards = new Deck(numDecks);
		players = new ArrayList<HumanPlayer>();
		dealer = new Dealer();
	}
	
	///////////////////////////// Methods /////////////////////////////////

	// Section 1: play()
	// Section 2: helper methods
	// Section 3: dialogue
	
	/////////////////////
	// blackjack logic
	/////////////////////
	
	/**
	 * play() executes the game
	 * 
	 */
	public void play() {
		
		Scanner scanner = new Scanner(System.in);
		introMessage();			// introduce game
		setUpPlayers(scanner);	// set the players up
		
		// While there are still players
		while(players.size() > 0) {
			
			initialBets(scanner);	// take a round of bets
			initialDeal(scanner);	// deal out the cards
			playerMoves(scanner);	// players move
			dealerTurn();			// dealer moves
			settleBets();			// bets are settled
			resetAll();				// reset players
		}
		
		// when all the players leave/lose
		exitMessage();
	}

	
	/////////////////////
	// Helper methods
	/////////////////////
	
	/**
	 * setUpPlayers asks for the number of people who want to play 
	 * 		and their names, creates the players with an initial balance,
	 * 		and adds them to the list
	 * 
	 * @param scanner
	 */
	public void setUpPlayers(Scanner scanner) {
		
		// Get number of players
		System.out.println("How many people are playing?");
		System.out.print("> ");
		int numPlayers = scanner.nextInt();
		
		// Buffer for readability
		System.out.println();
		scanner.nextLine();
		
		// Create players with input name
		for (int i = 0; i < numPlayers; i++) {
			String name = "Player " + (i+1);
			System.out.println(name + ": Enter Name");
			System.out.print("> ");
			
			// Use default name if line is blank!
			String input = scanner.nextLine();
			name = (input.length() != 0) ? input : name;
			players.add(new HumanPlayer(name, initialBalance));
		}
		System.out.println();
	}
	
	
	/**
	 * initialBets collects the starting bets for each of the players
	 * 
	 * @param scanner
	 */
	public void initialBets(Scanner scanner) {
		System.out.println("~NEW ROUND!~");

		List<HumanPlayer> removePlayers = new ArrayList<HumanPlayer>();
		for (HumanPlayer p : players) {
			// want to check for terminating conditions
			boolean successfulBet = false;

			while (!successfulBet) {
				System.out.println(p.getName() + ", whats your bet?");
				// prints current balance
				System.out.println("Current balance: $" + p.getBalance());
				System.out.print("> $");
				int bet = scanner.nextInt();
				
				if (bet == -999) {
					// TODO: Dialogue box for this option
					leaveMessage(p);
					removePlayers.add(p);
					successfulBet = true;
					
				} else if (bet < 0) {
					System.out.println("Invalid bet!");
					System.out.println();
					
				} else if (bet == 0 && players.size() > 1) {
					// if there's one player, skipping a turn is meaningless!
					p.setPlaying(false);
					successfulBet = true;
					
				} else if (bet > 0){
					successfulBet = p.bet(bet);
					
				} else {
					// we only get here if there's one player, and the balance is 0
					System.out.println();
					// let's have the entire text box!
					initialBets(scanner);
					successfulBet = true;
				}
			}
		}
		
		// get rid of players who exited
		for (HumanPlayer p : removePlayers) {
			players.remove(p);
		}
	}
	
	
	/**
	 * initialDeal deals out two cards to each of the players
	 * 		and the dealer.
	 * There is a small delay for ~dramatic effect~
	 */
	public void initialDeal(Scanner scanner) {
		if (players.size() == 0) return;
		
		cards.shuffle();
		System.out.println();
		
		// Deal them one by one, in order
		for (int i=0; i<2; i++) {
			for (Player p : players) {
				// make sure they aren't sitting out!
				
				if (p.isPlaying()) {
					Hand currentHand = p.getHand();
					currentHand.addToHand(cards.deal());
					// print the board after each deal
					currentState();
					
					// Add a slight delay
					try { Thread.sleep(dealDelay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// Handle dealer
			dealer.getHand().addToHand(cards.deal());
			currentState();
			try { Thread.sleep(dealDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Need to skip a line
		scanner.nextLine();
	}
	
	
	/**
	 * playerMoves goes through the players one by one, 
	 * 		and handles their turn
	 * 
	 * a player can hit until they bust, double, or stay
	 * 
	 *   input:		action:
	 *   "stay"		stays
	 *   "hit"		hits
	 *   "dub"		double-or-nothing
	 *   "split"	splits
	 *   "d"		shows dealer's hand
	 *   "?"		shows command descriptions
	 *   default	error message
	 * 
	 * @param scanner
	 */
	public void playerMoves(Scanner scanner) {
		
		// label loop as playerTurn
		playerTurn: for (HumanPlayer p : players) {
				
			while (p.isPlaying()) {
				System.out.println(p.getName() + ", what's the move?");
				System.out.println(p);
				System.out.print("> ");
				String option = scanner.nextLine();		
				
				switch (option) {
				case "stay": 	p.stay(); 
								break;
								
				case "hit": 	p.hit(cards.deal());
								System.out.println(p);		// print state
								checkBust(p, scanner);
								break;
								
				case "dub":		p.doubleDown(cards.deal());
								System.out.println(p); 
								checkBust(p, scanner);
								break;
				
				// In the split case, we want to add a player
				// 	and continue the loop:
				case "split":	split(p, scanner);
								// need to break out of everything,
								// or concurrent access exception
								break playerTurn;
								
				case "d" :		System.out.println(dealer); 
								break;
								
				case "?" :		playerMoveHelp();
								break;
								
				default: 		invalidErrorInput();
				}
				System.out.println();
			}
		}
	}
	
	
	/**
	 * split splits the current hand into two playable hands
	 * 	 	if your two cards are the same,
	 * 		you can afford to double your bet,
	 * 		and haven't split already
	 */
	public void split(HumanPlayer p, Scanner scanner) {
		System.out.println();
		
		// check if the hand is allowed to split
		if (!p.getHand().canSplit()) {
			System.out.println("Can't split!! Cards aren't the same!");
			
		// check if previously split
		} else if (p.getSplitPlayer() != null) {
			System.out.println("You've already split!!");
			
		// check if balance can handle splitting
		} else if (p.getBet()*2  > p.getBalance()) {
			System.out.println("Can't split!! Insufficient Balance.");
			
		} else {
			// we automatically start in the same location
			int insertLocation = players.indexOf(p);
			players.add(insertLocation+1, p.split());
		}
		playerMoves(scanner);
	}
	
	/**
	 * Checks and handles bust conditions after a hit or double down
	 * 
	 * @param player
	 * @param scanner
	 */
	public void checkBust(HumanPlayer player, Scanner scanner) {
		if (player.isBust()) {		// check if bust
			System.out.println();	// space for readability
			
			player.loseBet();		// handle loss
			System.out.print("Press to continue");
			scanner.nextLine();		// let user acknowledge
		}
	}
	
	
	/**
	 * the dealer draws until his hand is at least a soft 17
	 * 		(can configure dealer to hit on a soft 17)
	 * 
	 * if the dealer has busted, a print statement will alert the players
	 */
	public void dealerTurn() {
		if (players.size() == 0) return;

		System.out.println(dealer);
		// We can uncover the card!
		dealer.uncoverCard();
		
		// Sleep for drama
		try { Thread.sleep(dealDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Show the uncovered card
		System.out.println(dealer);
		
		// Dealer draws until soft 17
		while (dealer.isPlaying() && dealer.getHandValue() < 17) {
			
			// Sleep & Deal
			try { Thread.sleep(dealDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dealer.hit(cards.deal());
			System.out.println(dealer);
		}
		
		// Give the user a chance to see what happened
		try { Thread.sleep(dealDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (dealer.isBust()) System.out.println("Dealer bust!!");
		System.out.println();
	}
	
	
	/**
	 * settleBets() doles out the rewards!
	 * 
	 * If the player has busted, the player loses.
	 * Assuming the player didn't bust:
	 * 		if the dealer has busted, the player wins
	 * 		if the dealer's score is less than the player's, the player wins
	 * 		if the dealer's score is higher than the player's, the player loses
	 * 		if the dealer's score is equal to the player's, they tie
	 * 
	 */
	public void settleBets() {
		
		for (HumanPlayer p : players) {
			// busted players have already been handled!
			// some players may be sitting out!
			if (!p.isBust() && p.getBet() != 0) {
			
				// we win if the dealer is bust or player gets a higher score
				if (dealer.isBust() || p.getHandValue() > dealer.getHandValue() ) {
					p.winBet();
					
				// at this point, we know neither the player or the dealer has bust
				// we can compare values to determine loss/tie conditions
				} else if (p.getHandValue() < dealer.getHandValue()) {
					p.loseBet();
				} else {
					p.tie();
				}	
			}
		}
	}
	
	
	/**
	 * currentState prints the hand of all the players
	 * 		and the dealer
	 * 
	 * Uses the toString method in the Player class
	 */
	public void currentState() {
		for (Player p : players) {
			if (p.isPlaying())
				System.out.println(p);
		}
		System.out.println(dealer);
		System.out.println();
	}
	
	
	/**
	 * resetAll resets the players and dealer in between turns, 
	 * 		according to their implementations of reset,
	 * 		and handles all the split hands
	 * 
	 * resetAll also checks for players with no more money, and removes them
	 */
	public void resetAll() {
		// uses a listIterator to allow deleting while iterating
		ListIterator<HumanPlayer> iter = players.listIterator();
		while(iter.hasNext()){
			HumanPlayer p = iter.next();
			
			// transfer split money
		    HumanPlayer possibleSplit = p.getSplitPlayer();
		    if(possibleSplit != null && !p.equals(possibleSplit)) {
		    	possibleSplit.transferBalance(p);
		    	iter.remove();
		    }
		}
		
		// Need to handle in a separate loop in the event that
		// 		transferring money made the original player broke
		ListIterator<HumanPlayer> iter2 = players.listIterator();
		while(iter2.hasNext()){    
			HumanPlayer p = iter2.next();
			// check if the player is out of money
		    if(p.getBalance() == 0){
		    	// print the loss message and kick him
		    	lossMessage(p);
		        iter2.remove();
		    }
		    p.reset();
		}
		
		dealer.reset();
		System.out.println();
	}
	
	
	/////////////////////
	// Dialogue
	/////////////////////
	
	/**
	 * Called upon game initialization
	 */
	public void introMessage() {
		System.out.println("Welcome to Blackjack!!");
		System.out.println();
		System.out.println("Try to get to 21 without going over.");
		System.out.println("Bet $0 to abstain from a round, or $-999 to exit!");
		System.out.println("Input '?' during your turn to get the commands.");
		System.out.println();
	}
	
	
	/**
	 * Called when player inputs '?' 
	 */
	public void playerMoveHelp() {
		System.out.println();
		System.out.println("Commands:");
		System.out.println("'hit'	~ deals a card");
		System.out.println("'stay'	~ stays at current hand");
		System.out.println("'split'	~ splits the current hand into two");
		System.out.println("'dub' 	~ double or nothing");
		System.out.println("'d' 	~ peek at the dealer's hand");
		System.out.println("'?'     ~ see this message again!");
	}
	
	/**
	 * Called when player makes an invalid command
	 */
	public void invalidErrorInput() {
		System.out.println("Invalid command! Input '?' for help!");
	}

	/**
	 * Called when player runs out of money
	 */
	public void lossMessage(Player p) {	
		System.out.println("Darn, " + p.getName() + " is out of money.");
		System.out.println("Thanks for playing, and better luck next time!");
		System.out.println();
	}
	
	/**
	 * Called when player leaves the game
	 */
	public void leaveMessage(HumanPlayer p) {	
		System.out.println("We'll miss you! Thanks for playing");
		System.out.println("Total balance: " + p.getBalance() + " Net winnings: " + (p.getBalance()-1000));
		System.out.println();
	}
	
	/**
	 * Calls when game exits
	 */
	public void exitMessage() {
		System.out.println("Bye! Hope you enjoyed!!");
	}
	
	/////////////////////
	// Main method
	/////////////////////
	
	public static void main(String[] args) {
		Blackjack game = new Blackjack();
		game.play();
	}			
}

import java.util.Scanner;

public class BlackjackGame {
	
	private static int balance;
	private static Deck deck;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		playGame();
	}

	public static void playGame() {
		
		System.out.println("Welcome to Caesar's Palace Blackjack Table!");

		balance = 1000;
		System.out.println("Your starting balance is $" + balance);

		while(balance > 0) {
			playHand();
		}

		System.out.println("You're Broke!");
		System.out.println("Good luck finding a way home.");
	}

	public static void playHand() {
		deck = new Deck();
		deck.shuffle();
		int bet = 0;

		while(true) {
			System.out.print("Please enter a bet amount: ");
			bet = sc.nextInt();
			if (bet < 0) {
				System.out.println("I'll get the bouncer, if you try to take my money again...");
			}
			else if (bet < balance) {
				break;
			}
			else {
				System.out.println("You can't bet what you don't have...");
			}
		}

		System.out.println();

		BlackjackHand dealer = new BlackjackHand();
		BlackjackHand player = new BlackjackHand();

		player.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard());
		player.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard());

		if(dealer.getBlackjackValue() == 21) {
			System.out.println("Dealer has Blackjack!");
			balance = balance - bet;
			return;
		}

		while(true) {
			System.out.println("The dealer is showing " + dealer.getCard(0));
			System.out.println("Your hand is: ");
			for(int i = 0; i < player.getCardCount(); i++) {
				System.out.println(player.getCard(i));
			}
			System.out.println();
			System.out.println("Press 1 to hit or 2 to stay.");
			int move = sc.nextInt();
			if(move == 2) {
				break;
			}
			player.addCard(deck.dealCard());
			if(player.getBlackjackValue() > 21) {
				System.out.println(player.getCard((player.getCardCount() - 1)) + "!");
				System.out.println("Bust!");
				break;
			}
		}

		if (player.getBlackjackValue() <= 21) {
			System.out.println("Computer has: ");
			System.out.println(dealer.getCard(0));
			System.out.println(dealer.getCard(1));
			while(dealer.getBlackjackValue() < 17) {
				System.out.println("Dealer is hitting...");
				Card newCard = deck.dealCard();
				dealer.addCard(newCard);
				System.out.println(newCard);
			}
			System.out.println("You have " + player.getBlackjackValue());
			System.out.println("Dealer has " + dealer.getBlackjackValue());
			if(player.getBlackjackValue() >= dealer.getBlackjackValue() || (dealer.getBlackjackValue() > 21)) {
				System.out.println("Win " + bet + "!");
				balance = balance + bet;
			}
			else {
				System.out.println("Lose " + bet + "!");
				balance = balance - bet;  
			}
		}
		else {
			System.out.println("You have " + player.getBlackjackValue());
			System.out.println("Dealer has " + dealer.getBlackjackValue());
			System.out.println("Lose " + bet + "!");
			balance = balance - bet;
		}
	}
}
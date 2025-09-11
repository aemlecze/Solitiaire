package soliataire;
import java.util.*;

public class Board {
	ArrayList<sleeve> board;
	Deck deck = new Deck();
	Reserve reserve = new Reserve();
	ArrayList<Card> spares = new ArrayList<>();
	int moves = 0;
	
	public Board() {
		deck.Shuffle();
		sleeve one = new sleeve();
		one.addCard(deck.pop());
		sleeve two = new sleeve();
		two.addCard(deck.pop());
		two.addCard(deck.pop());
		sleeve three = new sleeve();
		three.addCard(deck.pop());
		three.addCard(deck.pop());
		three.addCard(deck.pop());
		sleeve four = new sleeve();
		four.addCard(deck.pop());
		four.addCard(deck.pop());
		four.addCard(deck.pop());
		four.addCard(deck.pop());
		sleeve five = new sleeve();
		five.addCard(deck.pop());
		five.addCard(deck.pop());
		five.addCard(deck.pop());
		five.addCard(deck.pop());
		five.addCard(deck.pop());
		sleeve six = new sleeve();
		six.addCard(deck.pop());
		six.addCard(deck.pop());
		six.addCard(deck.pop());
		six.addCard(deck.pop());
		six.addCard(deck.pop());
		six.addCard(deck.pop());
		sleeve seven = new sleeve();
		seven.addCard(deck.pop());
		seven.addCard(deck.pop());
		seven.addCard(deck.pop());
		seven.addCard(deck.pop());
		seven.addCard(deck.pop());
		seven.addCard(deck.pop());
		seven.addCard(deck.pop());
		board = new ArrayList<>(7);
		board.add(one);
		board.add(two);
		board.add(three);
		board.add(four);
		board.add(five);
		board.add(six);
		board.add(seven);
		
		for (Card card : deck.deck) {
			spares.add(card);
		}
	}
	public Board(Reserve reserve, ArrayList<Card> spare, int moves) {
		this.moves = moves;
		this.reserve = reserve;
		this.spares = spare;
	}
	
	public void addToReserve(Card card) {
		if (card.suit.equals(Suit.CLUB)) {
			this.reserve.club.addCard(card);
		}
		else if (card.suit.equals(Suit.SPADE)) {
			this.reserve.spade.addCard(card);
		}
		else if (card.suit.equals(Suit.HEART)) {
			this.reserve.heart.addCard(card);
		}
		else {
			this.reserve.diamond.addCard(card);
		}
	}
	public int getMoves() {
		return moves;
	}
	public void addMoves() {
		this.moves++;
	}
	
	public int size() {
		return board.size();
	}
	public sleeve getSleeve(int i) {
		sleeve sleeve = board.get(i);
		return sleeve;
	}
	public Reserve getReserve() {
		return reserve;
	}
	
	public ArrayList<Card> getSpare() {
		return spares;
	}
	
	public ArrayList<Card> getTips() {
		ArrayList<Card> tips = new ArrayList<>();
		for (sleeve sleeve : board) {
			for (Card card : sleeve.sleeve) {
				if (card.hidden == false) {
					tips.add(card);
				}
			}
		}
		return tips;
	}
	public Deck getDeck() {
		return deck;
	}
	
	public void init() {
		for (sleeve sleeve : board) {
			sleeve.peep().hidden = false;
		}
	}

	public void print() {
		for (sleeve sleeve : board) {
			sleeve.printSleeve();
		}
	}
	
	public Board copy() {
		int newMoves = this.moves;
	    Reserve newReserve = (this.reserve == null) ? null : this.reserve.copy();
	    ArrayList<Card> newSpare = (ArrayList<Card>) this.spares.clone();

	    Board newBoard = new Board(newReserve, newSpare, newMoves);
	    ArrayList<sleeve> newSleeves = new ArrayList<>();
	    for (sleeve s : this.board) {
	        newSleeves.add(s.copy());
	    }
	    newBoard.board = newSleeves;
	    newBoard.moves = this.moves;
	    return newBoard;
	}
}

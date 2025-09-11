package soliataire;
import java.util.ArrayList;
import java.util.Random;
public class Deck {
	ArrayList<Card> deck;
	
	//creates a standard deck of 52 cards.
	public Deck() {
		deck = new ArrayList<>(52);
		for (Suit suit : Suit.values()) {
			for (int values = 1; values <= 13; values++) {
				Card card = new Card(values, suit);
				deck.add(card);
			}
		}
	}
	
	public Deck copy() {
	    Deck newDeck = new Deck();
	    newDeck.deck.clear(); 
	    for (Card c : this.deck) {
	        newDeck.deck.add(c.copy());
	    }
	    return newDeck;
	}
	
	//size of deck
	public int size() {
		int size = deck.size();
		return size;
	}
	//gets the card at a certain index
	public Card getCard(int i) {
		Card card = deck.get(i);
		return card;
	}
	
	
	//randomizes deck order
	public void Shuffle() {
		Random random = new Random();
		  for (int i = deck.size() - 1; i > 0; i--) { 
	            int rand = random.nextInt(i + 1); 
	            Card currentIndexCard = deck.get(i);
	            Card currentRandomCard = deck.get(rand);
	            deck.set(i, currentRandomCard);
	            deck.set(rand, currentIndexCard);
	        }
	}
	
	public void print() {
		for (Card card : deck) {
			card.printCard();
		}
	}
	
	public Card pop() {
		int size = deck.size();
		Card card = deck.get(size - 1);
		deck.remove(size - 1);
		return card;
	}
	
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	public void add(Card card) {
		deck.add(0, card);
	}
}

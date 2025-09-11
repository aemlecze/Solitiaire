package soliataire;
import java.util.ArrayList;
public class sleeve {
	ArrayList<Card> sleeve;
	Suit suit;
	public sleeve() {
		sleeve = new ArrayList<>(50);
	}
	public void addCard(Card card) {
		sleeve.add(card);
	}
	
	public void remove(Card card) {
		sleeve.remove(card);
	}
	
	public ArrayList<Card> getRevealed() {
		ArrayList<Card> ret = new ArrayList<>();
		for (Card card : this.sleeve) {
			if (card.hidden == false) {
			ret.add(card);
			}
		}
		return ret;
	}
	
	public Card getFirst() {
		return this.sleeve.get(0);
	}
	public Card pop() {
		int size = 0;
		if (sleeve.isEmpty()) {
			Card card = new Card(100, Suit.CLUB);
			return card;
		}
		for (Card card : sleeve) {
			size++;
		}
		Card card = sleeve.get(size - 1);
		sleeve.remove(sleeve.indexOf(card));
		return card;
	}
	
	public void addLast(Card card) {
		sleeve.add(this.sleeve.size(), card);
	}
	
	public Card peep()   {
		int size = 0;
		if (sleeve.isEmpty()) {
			Card card = new Card(100, Suit.CLUB);
			return card;
		}
		
		for (Card card : sleeve) {
			size++;
		}
		Card card = sleeve.get(size - 1);
		return card;
	}
	
	public void printSleeve() {
		for (Card card : sleeve) {
			card.printCard();
		}
		System.out.print("\n");

	}
	public void replace(int i, Card card) {
		sleeve.set(i, card);
	}
	
	public boolean isEmpty() {
		return sleeve.isEmpty();
	}
	public sleeve copy() {
	    sleeve newSleeve = new sleeve();
	    newSleeve.suit = this.suit; 
	    for (Card c : this.sleeve) {
	        newSleeve.addCard(c.copy());
	    }
	    return newSleeve;
	}
}

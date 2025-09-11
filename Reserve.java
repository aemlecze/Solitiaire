package soliataire;
import java.util.*;

public class Reserve {
	sleeve heart;
	sleeve spade;
	sleeve diamond;
	sleeve club;
	
	public Reserve() {
		heart = new sleeve();
		heart.suit = Suit.HEART;
		heart.addCard(new Card(0, Suit.HEART));
		
		diamond = new sleeve();
		diamond.suit = Suit.DIAMOND;
		diamond.addCard(new Card(0, Suit.DIAMOND));
		
		club = new sleeve();
		club.suit = Suit.CLUB;
		club.addCard(new Card(0, Suit.CLUB));
		
		spade = new sleeve();
		spade.suit = Suit.SPADE;
		spade.addCard(new Card(0, Suit.SPADE));
	}
	

	
	public ArrayList<Card> reserveTops() {
		ArrayList<Card> tops = new ArrayList<>();
		if (heartpeep() != null) {
			tops.add(heartpeep());
		}
		if (diamondpeep() != null) {
			tops.add(diamondpeep());
		}
		if (spadepeep() != null) {
			tops.add(spadepeep());
		}
		if (clubpeep() != null) {
			tops.add(clubpeep());
		}
		return tops;
	}
	 
	public Card heartpeep() {
		if (heart.isEmpty()) {
			return null; 
		}
		return heart.peep();
	}
	public Card spadepeep() {
		if (spade.isEmpty()) {
			return null;
		}
		return spade.peep();
	}
	public Card clubpeep() {
		if (club.isEmpty()) {
			return null;
		}
		return club.peep();
	}
	public Card diamondpeep() {
		if (diamond.isEmpty()) {
			return null;
		}
		return diamond.peep();
	}
	
	public void printSleeves() {
		heart.printSleeve();
		club.printSleeve();
		spade.printSleeve();
		diamond.printSleeve();
	}
	
	public boolean isFull() {
		boolean ret = false;
		if (heart.sleeve.size() == 14 && club.sleeve.size() == 14 && spade.sleeve.size() == 14 && diamond.sleeve.size() == 14) {
			ret = true;
		}
		return ret;
	}
	public Reserve copy() {
	    Reserve newReserve = new Reserve();
	    newReserve.heart = this.heart.copy();
	    newReserve.spade = this.spade.copy();
	    newReserve.club = this.club.copy();
	    newReserve.diamond = this.diamond.copy();
	    return newReserve;
	}
}

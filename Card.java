package soliataire;

public class Card {
	Suit suit;
	int value;
	boolean hidden;
	Color color;

	public Card(int value, Suit suit) {
		this.suit = suit;
		this.value = value;
		hidden = true;
		if (suit.equals(Suit.SPADE) || suit.equals(Suit.CLUB)) {
			color = Color.BLACK;
		}
		else {
			color = Color.RED;
		}
	}
	public Card(int value, Suit suit, boolean hidden) {
		this.value = value;
		this.suit = suit;
		this.hidden = hidden;
		if (suit.equals(Suit.SPADE) || suit.equals(Suit.CLUB)) {
			color = Color.BLACK;
		}
		else {
			color = Color.RED;
		}
	}
	public Suit getSuit() {
		return this.suit;
	}
	public Color getColor() {
		return this.color;
	}
	public int getValue() {
		return this.value;
	}
	public boolean isHidden() {
		return this.hidden;
	}
	public String toString() {
		String string = suit.name();
		Integer in = value;
		String string1 = in.toString();
		string = string.concat(string1);
		return string;
	}
	public void printCard() {
		System.out.print(this.getValue() + "," + this.getSuit() + "   ");
	}
	
	
	public Card copy() {
	    Card newCard = new Card(this.value, this.suit);
	    newCard.hidden = this.hidden; 
	    return newCard;
	}
	
	
}

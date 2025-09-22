package soliataire;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.*;
import java.util.Scanner;


public class solitaireFinal {
		/*-------------------------------------*\
		 * Moves Class
		 * 
		 * Helps track moves. Uses sleeve numbers and the amount of cards
		 * from said sleeve to make a "move".
		 *-------------------------------------*/
		public class move {
		    Integer sourceSleeve;
		    int destinationSleeve;
		    int count; 
		    int index;
		    
		    public move(int index, int destinationSleeve) { 
		    	this.index = index;
		    	this.destinationSleeve = destinationSleeve;
		    }
		    public move(int sourceSleeve, int destinationSleeve, int count) {
		        this.sourceSleeve = sourceSleeve;
		        this.destinationSleeve = destinationSleeve;
		        this.count = count;
		    }
		    public String toString() {
		    	if (this.sourceSleeve != null) {
		    	Integer one = sourceSleeve;
		    	Integer two = destinationSleeve;
		    	Integer three = count;
		    	String string = " " + three + " cards from " + one + " sleeve to sleeve " + two; 
		    	return string;
		    }
		    	else {
		    		Integer one = index;
		    		Integer two = destinationSleeve;
		    		String string = "Take " + one + " from stack and move to " + two;
		    		return string;
		    	}
		}
		}
		
	    private long deadlineNanos = Long.MAX_VALUE;
		public void setTimeLimit(long value, TimeUnit unit) {
		    deadlineNanos = System.nanoTime() + unit.toNanos(value);
		    timedOut = false;
		}

	    private boolean timeUp() {
	        return System.nanoTime() >= deadlineNanos;
	    }

		
		/*-------------------------------------*\
		 * Returns a boolean of whether or not the comparison was successful
		 * O(1)
		 * 
		 * Compares opposite suit and lesser value
		 * Compares Kings and empty sleeves
		 *-------------------------------------*/
		
		public boolean compare(Card top, Card bottom) {
			boolean ret = false;
			if (top.getValue() == 100 && bottom.getValue() == 13) {
				ret = true;
			}
			if (!(top.getColor().equals(bottom.getColor())) && (top.getValue() == bottom.getValue() + 1)) {
				ret = true;
			}
			return ret;
		}
		
		/*-------------------------------------*\
		 * Returns a list of all possible moves on the board
		 * O(S^2+ (H(S)) + N) -> S = sleeves
		 * 						 H = stack
		 * 						 N = board
		 * 
		 * Iterates through the revealed cards, checks the stem of the card
		 * compares stem(root) with all the other sleeve tips.
		 *-------------------------------------*/
		// ERRORS:  EMPTY SLEEVES NOT REVEALED
		
		public List<move> getMoves(Board board) {
		    List<move> moves = new ArrayList<>();
		    ArrayList<Card> stack = board.getSpare();
		    
		    //get moves on board first
		    int i = 0;
		    for (sleeve from : board.board) {
		        List<Card> revealed = from.getRevealed();
		        if (revealed.isEmpty()) {
		        	i++; 
		        	continue;
		        }
		        Card root = revealed.get(0);
		        //compare empties and kings
		        if (root.getValue() == 13 && root == from.getFirst()) {
		        	i++;
		        	continue;
		        }
		        //compare cards on board (O(S^2))
		        //make move with sleeve numbers
		        int j = 0;
		        for (sleeve to : board.board) {
		            Card tip = to.peep();
		            if (compare(tip, root)) {
		                moves.add(new move(i, j, revealed.size()));
		            }
		            j++;
		        }
		        i++;
		    }
		    
		    //compare cards in stack (O(H(S))
		    //make move with index and destination sleeve
		    for (int h = 0; h < stack.size(); h++) {
		    	Card card = stack.get(h);
		    	int j = 0;
		    	for (sleeve sleeve : board.board) {
		    		Card sleeveTip = sleeve.peep();
		    		if (compare(sleeveTip, card)) {
		    			moves.add(new move(h, j));
		    		}
		    		j++;
		    	}
		    }
		    return moves;
		}
		
		/*-------------------------------------*\
		 * PerformMove returns nothing - O(toMove + toMove(card in sleeve)) 
		 * 
		 * 
		 * Using the board and the given move, performMove will manipulate
		 * the board to perform the chosen move. 
		 *-------------------------------------*/
		
		public void performMove(Board board, move m) {
			if (m.sourceSleeve != null) {
			sleeve source = board.getSleeve(m.sourceSleeve);
		    sleeve dest   = board.getSleeve(m.destinationSleeve);

		    List<Card> revealed = source.getRevealed();               
		    List<Card> toMove   = new ArrayList<>(revealed.subList(0, m.count));
		    for (Card c : toMove) {
		        source.remove(c);
		    }

		    for (Card c : toMove)  {
		        if (c.getValue() == 13 && dest.peep() != null && dest.peep().getValue() == 100) {
		            dest.pop();                    
		            dest.addCard(c);
		        } else {
		            dest.addCard(c);
		        }
		        c.hidden = false;                   
		    }

		    if (source.isEmpty())  {
		        source.addCard(new Card(100, Suit.CLUB, false));
		    } else {
		        Card newTop = source.peep();
		        if (newTop != null) newTop.hidden = false;   
		    }
		    board.addMoves();
			}
			
			else {
				Card c = board.getSpare().get(m.index);
				board.getSpare().remove(m.index);
				c.hidden = false;
				sleeve s = board.getSleeve(m.destinationSleeve);
				s.addCard(c);
				board.addMoves();
			}
		}
		
		/*------------------------------*\
		 * reserveCompare returns a boolean - O(1)
		 * 
		 * compares a card (from the board) with the reserve piles
		 *------------------------------*/
		public boolean reserveCompare(Card board, Card reserve) {
			boolean ret = false;
			if (board.getSuit().equals(reserve.getSuit()) && board.getValue() == reserve.getValue() + 1) {
				ret = true;
			}
			return ret;
		}
		
		
		/*-------------------------------*\
		 * reserveCheck returns a boolean O((cardsInSleeve + stackSize)^2)
		 * 
		 * checks if any cards on the board (not the stack)
		 * to see if they can be moved to the reserve
		 * 
		 * WILL CHECK UNTIL NO MOVES
		 *-------------------------------*/
		
		
		
		public boolean reserveCheck(Board board) {
		    boolean movedAny = false;

		    while (true) {
		        boolean movedThisPass = false;
		        ArrayList<Card> reserveTops = board.getReserve().reserveTops();
		       
		        for (sleeve slv : board.board) {
		            Card top = slv.peep();
		            if (top.getValue() == 100 || top.getValue() == 0) {
		            	continue;
		            }

		            for (Card rt : reserveTops) {
		                if (rt != null && reserveCompare(top, rt)) {
		                    // remove from sleeve
		                    slv.remove(top);
		                    
		                    //safety checks
		                    if (slv.isEmpty()) {
		                        slv.addCard(new Card(100, Suit.CLUB, false)); 
		                    } else {
		                        Card newTop = slv.peep();
		                        if (newTop != null) newTop.hidden = false;
		                    }

		                    // move to reserve
		                    board.addToReserve(top);
		                    
		                    //add and update
		                    board.addMoves();
		                    movedThisPass = true;
		                    movedAny = true;
		                    break; 
		                }
		            }
		        }
		        //traverse spares
		        for (ListIterator<Card> it = board.getSpare().listIterator(); it.hasNext(); ) {
		            Card card = it.next();
		            for (Card rt : board.getReserve().reserveTops()) { 
		                if (rt != null && reserveCompare(card, rt)) {
		                	//remove from spares (safe)
		                    it.remove();            
		                    
		                    //add to reserve
		                    board.addToReserve(card);
		                    
		                    // count the move, update booleans
		                    board.addMoves();             
		                    movedThisPass = true;
		                    movedAny = true;
		                    // move to next spare
		                    break;                       
		                }
		            }
		        }

		        if (!movedThisPass) {
		        	break; 
		        }
		    }
		    return movedAny;
		}
		
		
		/*-------------------------------*\
		 * Memoization - O(sleeves(sleeveSize) + spareSize)
		 * 
		 * Uses a hash map for instant retrieval
		 * concats the sleeves, stack and reserve (almost functions like a hash)
		 * and stores the instance.  We can check at the start of every run
		 * if this has been seen before
		 *-------------------------------*/
		private final java.util.HashSet<String> seen = new java.util.HashSet<>();
		private String fingerprint(Board b) {
		    StringBuilder sb = new StringBuilder();
		    for (sleeve s : b.board) {
		        for (Card c : s.sleeve) {
		            sb.append(c.getSuit()).append(',').append(c.getValue()).append(c.hidden?'h':'v').append('|');
		        }
		        sb.append('/');
		    }
		    sb.append('#');
		    sb.append(b.getReserve().heartpeep().getValue()).append(',');
		    sb.append(b.getReserve().diamondpeep().getValue()).append(',');
		    sb.append(b.getReserve().spadepeep().getValue()).append(',');
		    sb.append(b.getReserve().clubpeep().getValue());
		    sb.append('#');
		    for (Card c : b.getSpare()) {
		        sb.append(c.getSuit()).append(',').append(c.getValue()).append('|');
		    }
		    return sb.toString();
		}
		
		
		
		/*-----------------------------------*\
		 *  Play 
		 *  
		 *  Psuedo explanation
		 *  
		 *  play(board)
		 *  	if (base case) { return }
		 *		getMoves	  
		 *		if (no moves) {		
		 *			check reserve
		 *			play(board) reserve, else end
		 * 		else {
		 * 			perform move
		 * 			check reserve
		 * 			play(board)
		 * 		}
		 * 
		 *-----------------------------------*/

		static long time = System.currentTimeMillis();
		int counter = 0;
	    private boolean timedOut = false;
	    private int min = Integer.MAX_VALUE;
	    private int uniqueSolutions = 0;
	    
	    public boolean isTimedOut() { 
	    	return timedOut;
	   	}
	    public int bestMoves() { 
	    	return min;
	    }
	    public int solutionsFound() { 
	    	return uniqueSolutions; 
	    }
	    
		public boolean play(Board board) {
			long newTime = System.currentTimeMillis();
			if (newTime == time + 4000) {
				if (counter == 0) {
				System.out.println("Thinking.");
				time = newTime;
				}
				if (counter == 1) {
					System.out.println("Thinking..");
					time = newTime;
				}
				if (counter == 2) {
					System.out.println("Thinking...");
					time = newTime;
					counter = -1;
				}
				counter++;
			}
		    if (timeUp()) { 
		    	timedOut = true; 
		    	return uniqueSolutions > 0; 
		    }
		    if (board.getReserve().isFull()) {
		    	if (board.moves < min) {
		    		min = board.moves;
		    	}
		    	uniqueSolutions++;
		        return true;                          
		    }

		    String key = fingerprint(board);
		    if (!seen.add(key) || board.getMoves() > min) {
		        return false;                          
		    }

		    List<move> moves = getMoves(board);
		    if (moves.isEmpty()) {	
		    	Board copy = board.copy();
		    	boolean r = reserveCheck(copy);
		    	if (r == false) {
		    		return false;
		    	}
		    	else {
		    		return play(copy);
		    	}
		    }
		    else {
		        boolean anySolved = false;
		        for (move m : moves) {
		            Board copy = board.copy();
		            performMove(copy, m);
		            reserveCheck(copy);
		            if (play(copy)) anySolved = true;		       
		            }
		        return anySolved;                           
		    }
		}
		
		
		public static void main(String[] args) {
			solitaireFinal finale = new solitaireFinal();
			Board b = new Board();
			b.init();
			finale.setTimeLimit(1, TimeUnit.MINUTES);
			boolean t = finale.play(b);
			System.out.println("STARTING... THIS PROGRAM WILL TIME OUT IN 1 MINUTE");
			if (finale.isTimedOut()) { 
				System.out.println("TIMED OUT (took > 1 min)");
			}
			if (t) {
				b.print();
				System.out.println(b.getSpare().toString() + "\n");
				System.out.println("THIS PROGRAM FOUND " + finale.solutionsFound() + " SOLUTIONS AND CAN BE SOLVED IN A MINIMUM OF " + finale.bestMoves() + " MOVES");			}
			else {
				b.print();
				System.out.println("THIS BOARD CANNOT BE SOLVED...");
				System.out.println("https://www.theseus.fi/handle/10024/501330");
			}
		}
}



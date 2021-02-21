import java.util.*;
public class Card 
{
	public final static int DIAMONDS = 0;   // Codes for the 4 suits, plus Joker.
	public final static int CLUBS    = 1;
	public final static int HEARTS   = 2;
	public final static int SPADES   = 3;	
	   
	public final static int ACE   =  14;      // Codes for the non-numeric cards.
	public final static int JACK  = 11;    //   Cards 2 through 10 have their 
	public final static int QUEEN = 12;   //   numerical values for their codes.
	public final static int KING  = 13;
	
	private final int suit;
	private final int value;
	static int result;
	private static Card[] deck;
	private static List<Card> list = new ArrayList<Card>();

	public Card(int theValue, int theSuit)
	{
		value = theValue;
	    suit = theSuit;
	}
	public int getSuit()
	{
		return suit;
	}
	public int getValue() 
	{
		return value;
	}
	  public String getValueAsString() 
	  {
		  switch ( value ) {
		  	 case 1:   return "";
	         case 2:   return "2";
	         case 3:   return "3";
	         case 4:   return "4";
	         case 5:   return "5";
	         case 6:   return "6";
	         case 7:   return "7";
	         case 8:   return "8";
	         case 9:   return "9";
	         case 10:  return "10";
	         case 11:  return "Jack";
	         case 12:  return "Queen";
	         case 13:  return "King";
	         case 14: return "Ace";	
	         default: return "";
	         }
		    
	   }
	  public String getSuitAsString() {
	      switch ( suit ) {
	      case   SPADES: return "Spades"  ;
	      case   HEARTS: return "Hearts"  ;
	      case DIAMONDS: return "Diamonds";
	      default      : return "Clubs"   ;
	      }
	   }
	  public int length()
	  {
		  return deck.length;
	  }
	
	  public static List<Card> getDeck(){   //to assign values to each cards 

    	  deck = new Card[52];
	      int cardCt = 0; // How many cards have been created so far.
	      for ( int suit = 0; suit <= 3; suit++ ) {
	         for ( int value = 2; value <= 14; value++ ) {
	            deck[cardCt] = new Card(value,suit);
	            list.add(deck[cardCt]);
	            cardCt++;
	            
	         }
	      }	      
    	  
              return list;
	  }
	  
	  public int compareTo(Card c) {   //comparing the cards each round
    	  if (this.getValue() == c.getValue()){ 
    		return 0;
    	  }
    	  		else if (this.getValue() > c.getValue()){
    	  	return 1;
    	  	
    	  	}else
    	    return -1;
    	  }
	  
	  static int Compare4(int i, int j, int k, int l)
		{
			int [][] Compare=new int[][]{{1,i},{2,j},{3,k},{4,l}};
			
			
			//Actual Sorting
			Arrays.sort(Compare, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
			        Integer quantityOne = o1[1];
				    Integer quantityTwo = o2[1];
				    return quantityOne.compareTo(quantityTwo);
				}
		      });  // End of function call sort().
			
			
			//If Draw return 0
			if(Compare[2][1]==Compare[3][1])
			{
				result=0;
				return result;
			}
			//If Not Draw,Return the Player Number
			else
			{
				result=Compare[3][0];
				return result;
			}
			
		}
	  static int GetResult()
	  {
		  return result;
	  }
	  
	  public String toString() {
		  return getValueAsString() + " of " + getSuitAsString();
	  }
}


import java.util.*;


public class Game {

	int Numplayer;
	private boolean quit = false;
	List<Card> list1 = Card.getDeck(); //newly create card that never shuffle
	List<Card> list2; //list 2 store shuffled card
	 private int ID = 0; //player id
	 List<Card>[] PlayerDeck = new List[4]; //player deck
	 List<Card>[] war = new List[4]; //list for war
	 List<Card> selcard = new ArrayList<Card>(); //list to store player selected card
	 List<Card> usedcard = new ArrayList<Card>(); //list to store used card
	 List<Card> unusedcard = new ArrayList<Card>();//list to store unused card
	 Scanner input = new Scanner(System.in);
	protected List<Playerprof> Players = new ArrayList<Playerprof>();//create new player profile
	
	public Game()
	{};
	
	public List<Card> shuffled()
	{
		//pass shuffled card back
		Collections.shuffle(list1);
		list2 = list1;
		return list2 ;
	}

	public void getplayer(int j)
	{
		//get number of player
		Numplayer = j;
	}
	
	public int setplayer()
	{
		//return number of player
		return Numplayer;
	}
	
	public void multiplayer(int j)
	{
	 //create player profile
		Scanner input = new Scanner(System.in);
		//getplayer(j);
		for (int i = 0; i < Numplayer; i++)
		{
			 int id = i + 1;
			Playerprof Player = new Playerprof(id);
			Players.add(Player);
			System.out.print("Enter name for Player" + " " + (i+1)+": ");
			Player.setName(input.nextLine());
		}
	}
	
	public void cardMapping(List<Playerprof> Players){ 
		//distribute shuffled card to players
		list2 = shuffled();
		this.Players = Players;
		int i = 0;
		int k = 0;// mapping shuffled cards to all the players
		for (Playerprof pl : Players) {
			PlayerDeck[i] = new ArrayList<Card>();
			int cardLimit = k + 52/Numplayer;
			for (int a = k; a < cardLimit; a++){
				PlayerDeck[i].add(list2.get(a));	 
			}
			 k = cardLimit;
			 i++;
		}
		startgame(Players);
}
	
	public void initializeGame(int j)
	{
		
		Numplayer = j;
		multiplayer(Numplayer);	
		cardMapping(Players);
		}
	
	public void startgame(List<Playerprof>Players) //for 2 players //here will start a new round of game and used 1 card to fight
	{
		do {
		int onwar = 0;
		int i = 0;
		Card maxCard = null, c = null;
		Playerprof maxPlayer = new Playerprof();   //create new player profile
		
        System.out.println("\nStart Game! ");
        
        for (int y = 0; y < Numplayer; y++) {
			Playerprof player = getNextPlayer();  //get player profile  
			System.out.println("It's turn for   " + player.getName());
        	System.out.println("1. Display card   \n2. Stop Game");
          	System.out.print("Please select : ");
          	//int s = input.nextInt(); //let user to select card
			int s =1; //auto selection
          	
          	switch (s) 
          		{
          	case 1:  
          			for (int u = 0; u < PlayerDeck[y].size();)
				 	{
			           	 System.out.print("< "+(++u) + " >");  //display eg: <1><2><3>...
			        }
          			
          			System.out.print("\nPlease select a card: ");
          			//i = input.nextInt();   //user input to select which card
					i=1;    // auto selection
					c = PlayerDeck[y].get(i - 1); //store selected card into variable
					System.out.println("Card Selected ==> " + c.toString()+"\n") ; //print out the selected card
					PlayerDeck[y].remove(i - 1); // remove the card from the player deck
					selcard.add(c);// add the selected card into list of selected card
					usedcard.add(c); //add the selected card into used card list
					break;
					
          	case 2:
          			System.out.println("---Game exit---");
          			System.exit(0);
          			break;
          			 
          		
          		}
          	
          		if (maxCard == null){
          			maxCard = c;
          			maxPlayer = player;
          		}else
          		{
          			if (maxCard.compareTo(c) < 0) {                    //compare and find highest
            			 maxCard = c;
            			 maxPlayer = player;
            			 System.out.println("The Winner is " + maxPlayer.getName());
            			 PlayerDeck[1].addAll(usedcard); //add the used card to winner
            			 usedcard.clear(); //clear the used card after added
            			 Collections.shuffle(PlayerDeck[1]); //shuffle player's deck for winner after added 
            			 
          			}else if(maxCard.compareTo(c) > 0)
          			{
          				System.out.println("The Winner is " + maxPlayer.getName());
          				PlayerDeck[0].addAll(usedcard);//add the used card to winner
          				 usedcard.clear();//clear the used card after added
          				Collections.shuffle(PlayerDeck[0]);//shuffle player deck
          			}else if(maxCard.compareTo(c) == 0)
          			{
          				// if draw go to war
          				onwar = 1;
          			}
          		}    
        }
        if(onwar == 1)
      	{
        	if(PlayerDeck[0].size() >= 4 && PlayerDeck[1].size() >= 4) { //check whether enough card or not
      		firstwar();
        	}else if(PlayerDeck[0].size() < 4) //if deck size not at least 4
        	{
        		for (int b = 0; b<1; b++)
        		{
      				Playerprof player = getNextPlayer(); //get player profile
      				if(player.getPlayer() == 1)
      				{
      					System.out.println("Sorry, Player " + player.getName() + " is not enough card to play war!");
      					unusedcard.addAll(PlayerDeck[0]); //add all lose player's card to unused card
      					PlayerDeck[1].addAll(usedcard); //add the usedcard to winner
      					quit = true;
      				}
        		}
        	}else if(PlayerDeck[1].size() < 4)
        	{
        		for (int b = 0; b<2; b++) 
        		{
        			Playerprof player = getNextPlayer();
        			if(player.getPlayer() == 2)
        				{
        				System.out.println("Sorry, Player " + player.getName() + " is not enough card to play war!");
        				unusedcard.addAll(PlayerDeck[1]);//add all lose player's card to unused card
        				PlayerDeck[0].addAll(usedcard);//add the usedcard to winner
        				quit = true;
        				}
        		}
        	}
		}
        if (maxPlayer.getPlayer() > 0 ) {
            maxCard = null;
            maxPlayer = null;
		}
        
		}while(PlayerDeck[0].size() >= 1 && PlayerDeck[1].size() >= 1 && quit == false); //check whether to continue game or not
		displayfinalresult();
	}
	
	 public void firstwar()
     {
		
		int warr = 0;
		int i;
		Card maxCard = null, c = null;
		Playerprof maxPlayer = new Playerprof();
		System.out.println("W A R");
		for (int y = 0; y < Numplayer; y++)
		{
			Playerprof player = getNextPlayer();
			war[y] = new ArrayList<Card>();  //card store inside here after they used by player
			for (int u = 0; u < PlayerDeck[y].size();){
           	 System.out.print("< "+(++u) + " >");
            }
			System.out.println();
			System.out.println("Player"+(y+1)+":"+player.getName() + "\nPlease select 4 cards with 3 faced down and one faced up :"); //prompt user to get 4 card
			for (i = 0; i < 4;i++) 
			{
				System.out.print("Please select : ");
				//int s = input.nextInt();
				int s=1; //autoselection
			
				if (i < 3) {
					c = PlayerDeck[y].get(s - 1); //store selected card in variable
					war[y].add(PlayerDeck[y].get(s - 1));//store selected war card in to war list
					PlayerDeck[y].remove(s - 1);//remove the selected card from player
					usedcard.add(c);//store selected card in usedcard
					System.out.println("Card "+ (i+1) +" selected : XX");
				}else 
				{
					c = PlayerDeck[y].get(s - 1);//store selected card in variable
					war[y].add(PlayerDeck[y].get(s - 1));//store selected card war list
					PlayerDeck[y].remove(s - 1);//remove card
					usedcard.add(c);//store selected card in usedcard
					System.out.println(" Card "+ (i+1) +" selected : " + c.toString());
				}
			}
			if (maxCard == null){
      			maxCard = c;
      			maxPlayer = player;
      		}else
      		{
      			if (maxCard.compareTo(c) < 0) {
        			 maxCard = c;                                                          //compare and find highest
        			 maxPlayer = player;
        			 System.out.println("The Winner is " + maxPlayer.getName());
        			 PlayerDeck[1].addAll(usedcard);										   //the winner will take all usedcard
        			  usedcard.clear();
      			}else if(maxCard.compareTo(c) > 0)
      			{
      				System.out.println("The Winner is " + maxPlayer.getName());
      				PlayerDeck[0].addAll(usedcard);
      				usedcard.clear();
      			}else if(maxCard.compareTo(c) == 0)
      			{
      				warr = 1;
      			}
         	 }
		System.out.println();
		}
		if(warr > 0)
      	{
			if(PlayerDeck[0].size() >=1 && PlayerDeck[1].size() >=1)	//check whether enough card to conitnue war
			secondwar();
      	}else
      	{
      		if(PlayerDeck[0].size() == 0)//check whether enough card to conitnue war
        	{
        		for (int b = 0; b<1; b++)
        		{
      				Playerprof player = getNextPlayer();
      				if(player.getPlayer() == 1)
      				{
      					System.out.println("Sorry, Player " + player.getName() + " is not enough card to play second war!");
      					unusedcard.addAll(PlayerDeck[0]);//add loser card to unsedcard list
      					PlayerDeck[0].addAll(usedcard);//add used card to winner
      					quit = true;
      				}
        		}
        	}else if(PlayerDeck[1].size() == 0)//check whether enough card to conitnue war
        	{
        		for (int b = 0; b<2; b++) 
        		{
        			Playerprof player = getNextPlayer();
        			if(player.getPlayer() == 2)
        				{
        				System.out.println("Sorry, Player " + player.getName() + " is not enough card to play second war!");
        				unusedcard.addAll(PlayerDeck[1]);// add loser card to unused card
        				PlayerDeck[0].addAll(usedcard);// add usedcard to winner
        				quit = true;
        				}
        		}
        	}
      	}
		if (maxPlayer.getPlayer() > 0 ) {
			maxCard = null;
            maxPlayer = null;
		}
     }
	 
	 public void secondwar()
	 {
		 int warr = 0;
			Card maxCard = null, c = null;
			Playerprof maxPlayer = new Playerprof();
			System.out.println("W A R AGAIN!!");
			for (int y = 0; y < Numplayer; y++)
			{
				Playerprof player = getNextPlayer();
				for (int u = 0; u < PlayerDeck[y].size();){
	           	 System.out.print("< "+(++u) + " >");
	            }
				
				System.out.println();
				System.out.print("Player"+(y+1)+":"+player.getName()+ ", please select 1 cards faced down: ");
	//			int s = input.nextInt();
				int s=1;//autoselect
				c = PlayerDeck[y].get(s - 1);
				PlayerDeck[y].remove(s - 1);//put the selected card into c
					war[y].add(c);
					usedcard.add(c);
					System.out.println(" card "+ s +" selected : XX"); //fourth card face-down
					System.out.println(" card "+ s +" selected : "+ war[y].get(1));
				
					if (maxCard == null){
	          			maxCard = c;
	          			maxPlayer = player;
	          		}else
	          		{
	          			if (maxCard.compareTo(c) < 0) {
	            			 maxCard = c;
	            			 maxPlayer = player;
	            			 System.out.println("The Winner is " + maxPlayer.getName());
	            			 PlayerDeck[1].addAll(usedcard);
	         	            usedcard.clear();
	          			}else if(maxCard.compareTo(c) > 0)
	          			{
	          				System.out.println("The Winner is " + maxPlayer.getName());
	          				PlayerDeck[0].addAll(usedcard);
	        	            usedcard.clear();
	          			}else if(maxCard.compareTo(c) == 0)
	          			{
	          				warr = 1;
	          			}
	          			
	          	 }
			}
			if(warr > 0)
	      	{
				if(PlayerDeck[0].size() >=1 && PlayerDeck[1].size() >=1)
	      		secondwar();
	      	}
			if (maxPlayer.getPlayer() > 0 ) {
				maxCard = null;
	            maxPlayer = null;
			}
			
	 }
		

protected Playerprof getNextPlayer(){ // passing to next player

	//get next player
    Playerprof p = null;
    if (ID == Players.size()){
    	ID = 1;
        p = Players.get(0);
    }
    else{
            p = Players.get(ID);
            ID++;
    }
    return p;
}

public void displayfinalresult()
{
	//display result
	int mostcard = 0, posmostplayer = 0;
	System.out.println("\n\nFinal result:");
	System.out.println("Sorry,there is one player enough card to play.");
	for (int i=0;i<Numplayer;i++)
	{		
		@SuppressWarnings("unused")
		Playerprof player = getNextPlayer();
		if(PlayerDeck[i].size() > mostcard) {
		mostcard = PlayerDeck[i].size() + unusedcard.size();
		posmostplayer = i;
		}
	}
	
	for (int i=0;i<Numplayer;i++)
	{
		Playerprof player = getNextPlayer();
		if(player.getPlayer() == posmostplayer+1)
        	System.out.println("Player " + player.getName() +" has Won The Game with "+mostcard+""+"cards.");
	}
}
}



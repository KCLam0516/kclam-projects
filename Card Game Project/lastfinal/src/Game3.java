import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game3 extends Game {
	Game2 g = new Game2();
	@Override
	public void cardMapping(List<Playerprof> Players){ 
		//shuffled the card and distribute to all the player
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
		startgame2(Players);
}
	
	public void initializeGame(int j)
	{
		//pass the needed value to initialize the game
		Numplayer = j;
		multiplayer(Numplayer);	
		cardMapping(Players);
		}
	
	public void startgame2(List<Playerprof>Players)
	{
		
		do {
			selcard.clear(); //clear the list of selected card
			int i = 0;
			
			Card c = null;
	
			System.out.println("\nStart Game! ");
    
			for (int y = 0; y < Numplayer; y++) 
			{
				Playerprof player = getNextPlayer();  //get player profile
				System.out.println("Player "+ player.getName()+" turn:");
				System.out.println("1. Display card   \n2. Stop Game");
				System.out.println("Please select : ");
  			//    int s = input.nextInt();    //user input to select which card
				int s=1;   // auto selection
				switch (s) {					
				case 1:  
					for (int u = 0; u < PlayerDeck[y].size();)
					{
						System.out.print("< "+(++u) + " >");
					}
//  				i = input.nextInt();   //user input to select which card
					i=1;    // auto selection
					System.out.println();
					c = PlayerDeck[y].get(i - 1);   //store selected card into variable
					System.out.println("Card Selected ==> " + c.toString()+"\n"); //print out the selected card
					PlayerDeck[y].remove(i - 1); // remove the card from the player deck
					selcard.add(c);// add the selected card into list of selected card
					usedcard.add(c); //add the selected card into used card list
					break;
				case 2:
					System.out.println("---Game exit---");
	          		System.exit(0);
	          		break;
					
				}
			}
  		
			Card.Compare4(selcard.get(0).getValue(),selcard.get(1).getValue(),selcard.get(2).getValue(),selcard.get(3).getValue());//Compare the 4 display card
			for(int k=1;k<=Numplayer;k++)
			{
				Playerprof player = getNextPlayer();
				if(Card.GetResult()==k)
				{
					PlayerDeck[k-1].addAll(usedcard); // add all the card used to winner //maxplayer=playerindex//playerss[i]=deck[i]
					usedcard.clear();
					Collections.shuffle(PlayerDeck[k-1]); //shuffle the list of winner
					System.out.println("The Winner is " + player.getName());
				}
			}
			if(Card.GetResult()==0)
			{
				for(int p = 0; p <Numplayer; p++)
				{
					Playerprof player = getNextPlayer();  //create new player profile
					if(PlayerDeck[p].size() < 1)
					{
						System.out.println("Sorry, Player " + player.getName() + " is not enough card to play war!"); //tell player that he is not enough card to play war
						unusedcard.addAll(PlayerDeck[p]); //add all the player card that already lose to unused card
						PlayerDeck[p].clear();  //clear the player deck because already lose
						//Players.remove(p); //remove player profile
					}
				}
			}
			int gg=0;
			if(Card.GetResult()==0 && PlayerDeck[0].size() >= 4 && PlayerDeck[1].size() >= 4 &&PlayerDeck[2].size() >= 4 && PlayerDeck[3].size() >= 4)
				firstwar1();
			else if(Card.GetResult()==0&&PlayerDeck[0].size() < 4 && PlayerDeck[1].size() < 4 &&PlayerDeck[2].size() < 4&& PlayerDeck[3].size() < 4)
				 gg=1; //gg is to set to end if there exist a war but there is a player enough card to play 
			
    	
		if(gg==1||Players.size() < 2) //if there is less than 2 player remaining, call result
		break;

		}while(PlayerDeck[0].size() >= 1 && PlayerDeck[1].size() >= 1 && PlayerDeck[2].size() >= 1 && PlayerDeck[3].size() >= 1);//check whether enough card or not to continue game
		displayfinalresult();
	}

	public void firstwar1()
	{
		int i;
		Card c = null;
		System.out.println("W A R");
		for (int y = 0; y < Numplayer; y++)
		{
			Playerprof player = getNextPlayer();//get player profile
			war[y] = new ArrayList<Card>();  //card store inside here after they used by player
			for (int u = 0; u < PlayerDeck[y].size();)
			{
				System.out.print("< "+(++u) + " >");
			}
			
			System.out.println();
			System.out.println("Player Name:"+player.getName() + "\nplease select 4 cards with 3 faced down and one faced up :");
			for (i = 0; i < 4;i++) 
			{
//				int s = input.nextInt(); //let user to select 4 card
				int s =1; //auto selection
				if (i < 3) {
					c = PlayerDeck[y].get(s - 1); //store selected card into variable
					war[y].add(PlayerDeck[y].get(s - 1)); // add selected card into a war list of player
					PlayerDeck[y].remove(s - 1); //remove the selected card from deck
					usedcard.add(c); //add the card into used card
					System.out.println(" Card "+ (i+1) +" selected : XX");
				}
				else 
				{
					c = PlayerDeck[y].get(s - 1);
					war[y].add(PlayerDeck[y].get(s - 1));
					PlayerDeck[y].remove(s - 1);
					usedcard.add(c);
					System.out.println(" Card "+ (i+1) +" selected : " + c.toString());
				}
			}
		}
		Card.Compare4(war[0].get(3).getValue(),war[1].get(3).getValue(),war[2].get(3).getValue(),war[3].get(3).getValue());
		for(int k=1;k<=Numplayer;k++)
		{
			Playerprof player = getNextPlayer();
			if(Card.GetResult()==k)
			{
				PlayerDeck[k-1].addAll(usedcard); // add all the card used to winner //maxplayer=playerindex//playerss[i]=deck[i]
				usedcard.clear();
				Collections.shuffle(PlayerDeck[k-1]); //shuffle the list of winner
				System.out.println("The Winner is " + player.getName());
			}
		}
		if(PlayerDeck[0].size() < 1 || PlayerDeck[1].size() < 1 || PlayerDeck[2].size() < 1||PlayerDeck[3].size()<1)
		{
		for(int p = 0; p <Numplayer; p++)
		{
			Playerprof player = getNextPlayer();  //get player profile
			if(PlayerDeck[p].size() < 1)
			{
				System.out.println("Sorry, Player " + player.getName() + " is not enough card to play war!"); 
				unusedcard.addAll(PlayerDeck[p]); // add the player deck into unused card
				PlayerDeck[p].clear();// clear the player deck that lose
			}
		}
		}
		if(Card.GetResult()==0) //if there is second war, only 1 card in deck is needed for each player
		{
			secondwar1();
		}
	
//		if(Players.size() < 2) //if there is less than 2 player remaining, call result
//				displayfinalresult();
		}
	

	public void secondwar1()
	{
		Card c = null;
		System.out.println("W A R AGAIN!!");
		for (int y = 0; y < Numplayer; y++)
		{
			Playerprof player = getNextPlayer();//get different player when loop
			for (int u = 0; u < PlayerDeck[y].size();)
			{
				System.out.print("< "+(++u) + " >");
			} 
			System.out.println();
			System.out.print("Player Name:"+player.getName() + "\nplease select 1 cards faced down: ");
			for (int u = 0; u < PlayerDeck[y].size();)
			{
				System.out.print("< "+(++u) + " >");
			}
//			int s = input.nextInt();      //user input to select which card to fight with other players
			int s =1; // auto selection
			c = PlayerDeck[y].get(s - 1); // store selected card into a variable
			PlayerDeck[y].remove(s - 1);//put the selected card into c
			war[y].add(c); //add new selected card that face down  into the war list of players
			usedcard.add(c);
			System.out.println(" card "+ s +" selected : XX"); //fourth card face-down

			System.out.println(" card "+ (s+1) +" selected : " + war[y].get(1)); //get the first card from the war list of player
		}
		Card.Compare4(war[0].get(1).getValue(),war[1].get(1).getValue(),war[2].get(1).getValue(),war[3].get(1).getValue());
		for(int k=1;k<=Numplayer;k++)
		{
			Playerprof player = getNextPlayer();
			if(Card.GetResult()==k)
			{
				PlayerDeck[k-1].addAll(usedcard); // add all the card used to winner //maxplayer=playerindex//playerss[i]=deck[i]
				usedcard.clear();
				Collections.shuffle(PlayerDeck[k-1]); //shuffle the list of winner
				System.out.println("The Winner is " + player.getName());
			}
		}
		for(int p = 0; p <Numplayer; p++)
		{
			Playerprof player = getNextPlayer();  //get player profile
			if(PlayerDeck[p].size() <= 1)
			{
				System.out.println("Sorry, Player " + player.getName() + " is not enough card to play second war!"); 
				unusedcard.addAll(PlayerDeck[p]); // add the player deck into unused card
				PlayerDeck[p].clear();// clear the player deck that lose
			}
		}
	
		if(Card.GetResult()==0) //if there is second war, only 1 card in deck is needed for each player
		{
		    if(Players.size() >= 2)//if there is 2 player remaining, call secondwar()
		    		secondwar1();
//		    else if(Players.size() < 2) //if there is less than 2 player remaining, call result
//						displayfinalresult();
		}
		}
	}

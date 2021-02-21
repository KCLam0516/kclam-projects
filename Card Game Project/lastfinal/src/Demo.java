import javafx.application.*;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;

public class Demo extends Application {
	Scanner input = new Scanner(System.in);
    Stage window;
    Scene menuPage, rulePage;

    public static void main(String[] args) {
    	Scanner input = new Scanner(System.in);
        launch(args);
        input.close();
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        //Label Setup
        Label title = new Label("WELCOME TO THE WAR GAME!");
        Label ruleParagraph = new Label("Welcome to OOP Card game name War!\n"
        		+ "War RulesWar is a very simple card game for two to 4 players.\n"
        		+ "It's mostly a kids game,since it relies exclusively on luck of the draw.\n"
        		+ "1. A standard deck of 52 cards is dealt so that both players have 26, 17 or 13 cards.\n"
        		+ "2. Lowest to highest is:2,3,4,5,6,7,8,9,10,J,Q,K,A. Suits are ignored in this game.\n"
        		+ "3. Players should not look at their cards, or arrange them in any certain order.\n"
        		+ "4. Flip your top cards over at the same time. The card with the higher value wins, "
        		+ "and the winner takes\n     both cards, placing them face up at the bottom of their pile.\n"
        		+ "5. If you flip two cards of equal value, war begins.\n"
        		+ "  5.1-Each player turns up three card face down and one card face up."
        		+ "\n  5.2-The player with the higher cards takes both piles (ten cards)."
        		+ "\n  5.3-If the turned-up cards are again the same rank, each player places "
        		+ "another card face down and turn\n        another card face up.\n "
        		+ "  The player with the higher card takes all cards, and so on\n"
        		+ "6. If you don't have enough cards to play/war, you lose.\n"
        		+ "7. The game continues until one player has lose all the cards.\n"
        		+ "8. Last, it will display the winner which has the highest number of cards.\n");
        //Button Setup
        Button rulebutton = new Button("RULE");
        Button startbutton = new Button("START");
        Button quitbutton = new Button("QUIT");
        //Button Style
        rulebutton.setStyle(" -fx-font-size: 30px; -fx-background-color: linear-gradient(#ff5400, #be1d00);"
        		+ "-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;-fx-cursor: pointer;");
        startbutton.setStyle(" -fx-font-size: 30px; -fx-background-color: linear-gradient(#ff5400, #be1d00);"
        		+ "-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        quitbutton.setStyle(" -fx-font-size: 30px; -fx-background-color: linear-gradient(#ff5400, #be1d00);"
        		+ "-fx-background-radius: 30;-fx-background-insets: 0;-fx-text-fill: white;");
        title.setStyle("-fx-text-fill: black;-fx-font-weight: bold;-fx-font-size: 38px;");
        ruleParagraph.setStyle("-fx-text-fill: white;-fx-font-weight: bold;-fx-font-size: 14px;");
        rulebutton.setOnAction(e -> window.setScene(rulePage));
        window.setOnCloseRequest(e -> {
        	e.consume();
        	closeprogram();
        });
        //Entering game if start button click
        startbutton.setOnAction(e ->{
        	window.close();
    		boolean isNum;
        	//i=1; use this if you lazy
        	int i =1;
        	switch(i)
        	{
        	case 1:			
        		do
        		{
        		System.out.print("Enter number of players. (2 - 4): ");
        			
        			do 
        			{    //CHECK INVALID INPUTS
        				if (input.hasNextInt())
        				{
        					//i = input.nextInt();
        					isNum = true;
                            
        				}
        				else 
        				{
                        	
        					System.out.print("Error:Only Integer are allowed,please retry again.\nEnter Number Of Players. (2 - 4): ");
        					isNum = false;
        					input.next();
        				}
                    	} while (!(isNum));
        			
        			
        				i = input.nextInt();
        			//i=2//i=3 put here if you lazy
        			}while (i < 2 || i > 4);
        			
        			if (i == 2){
        				Game g = new Game();
        				g.initializeGame(i);
        			}else if (i == 3) {
        				Game g = new Game2();
        				g.initializeGame(i);
        			}else if(i == 4) {
        				Game g = new Game3();
        				g.initializeGame(i);
        			}
        			break;
        		case 2:
        			input.close();
        			System.out.println("\n-----EXIT GAME-----");
        			System.exit(0);
        		}
        	int a;
        	do{
        	System.out.println("\n\nDo you want to play again?\n1.Play again\n2.Quit");
        	System.out.print("Please select : ");
        	a=TakeInput();
        	if(a==1)
        	{
        		start(primaryStage);
        	}
        	else if(a==2)
        	{
        		System.out.println("---Game exit---");
        		System.exit(0);
        	}
        	}while(a!=1 && a!=2);  
        });
        quitbutton.setOnAction(e -> {
        	closeprogram();
		});

        //Layout 1 - Group button together + set Menu Page
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(title, rulebutton,startbutton,quitbutton);
        layout1.setAlignment(Pos.CENTER);
        menuPage = new Scene(layout1, 700, 400);
        layout1.setStyle("-fx-background-color: white;");


        //backbutton
        Button backbutton = new Button("BACK");
        backbutton.setOnAction(e -> window.setScene(menuPage));
        //button style
        backbutton.setStyle(" -fx-background-color: linear-gradient(#f2f2f2, #d6d6d6);"
        		+ "-fx-background-radius: 8,7,6;-fx-background-insets: 0,1,2;"
        		+ "-fx-text-fill: black;-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");

        //Group things together + set Rule Page
        VBox layout2 = new VBox(20);
        layout2.getChildren().addAll(ruleParagraph,backbutton);
        rulePage = new Scene(layout2, 800, 400);
        layout2.setAlignment(Pos.CENTER);
        layout2.setStyle("-fx-background-color: blue;");

        //Display scene 1 at first
        window.setScene(menuPage);
        window.setTitle("WAR WAR WAR WAR WAR");
        window.show();
    }

	private void closeprogram() {
		Boolean answer = ConfirmBox.display("TwT One more round!","Sure you wan to exit?");
		if(answer)
			window.close();
	}
	public static int TakeInput()//check input for integer only
	{
			int num = 0;
			
			Scanner input = new Scanner(System.in);
	        boolean success = true;
	        while (success) 
	        {
	            try {
	                num = input.nextInt();
	                success = false;
	            } catch (InputMismatchException e) 
	            {
	                input.next();
	                
	                System.out.println("!!! Invalid Input !!!");
	                System.out.print("Please, Enter an integer: ");
	            }
	        }
	        return num;
	}
}

public class Playerprof 
{

	private int Player;//id
	 private int Scores;
	 private String Name;
	 private String result;
	 
	public Playerprof(int x) 
	{
		this.Player = x;
    }
	
	public Playerprof() 
	{
		
    }   
	
	public int getPlayer(){ //identity of player ex: player 1 , 2 ,3...
       	return Player;
    }
	
	   public void setPlayer(int Player) { 
       	this.Player = Player;
       }

	  public String getName(){ //get name of player from input
      	return Name;
      }
	  
	 public void setName(String Name)
	 { 
     	this.Name = Name;
     }
	 
	 public int getScores(){ // get score from gameplay
     	return Scores;
     }

	 public void setScores(int Scores){
     	this.Scores = Scores;
     }

	public String getResult() { 
    	return result;
	}
	
	public void setResult(String result){
    	this.result = result;
    }
    

}

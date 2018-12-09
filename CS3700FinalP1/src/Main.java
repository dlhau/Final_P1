import java.io.IOException;
import java.util.Scanner;

/*
 * David Hau
 * CS3700
 * 12/7/2018
 */

public class Main
{
	static int playerOneTotal = 0, playerTwoTotal = 0, playerThreeTotal = 0;
	static int playerOneScore = 0, playerTwoScore = 0, playerThreeScore = 0;
	
	public static void main(String[] args) throws IOException
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the number of games to play: ");
		int numGames = keyboard.nextInt();
		
		for (int i = 1; i <= numGames; i++)
		{
			System.out.println("Game " + i + ":");
			try
			{
				Player playerOne = new Player(1);
				Player playerTwo = new Player(2);
				Player playerThree = new Player(3);

				boolean[] playerOneResults = 
					{ playerOne.futureOne.get(), playerOne.futureTwo.get() };

				boolean[] playerTwoResults = 
					{ playerTwo.futureOne.get(), playerTwo.futureTwo.get() } ;

				boolean[] playerThreeResults = 
					{ playerThree.futureOne.get(), playerThree.futureTwo.get() };

				evaluateScore(playerOneResults, 1);
				evaluateScore(playerTwoResults, 2);
				evaluateScore(playerThreeResults, 3);
				
				if (playerOneScore == 1 && playerTwoScore == 1 && playerThreeScore == 1)
				{
					playerOneScore = 0;
					playerTwoScore = 0;
					playerThreeScore = 0;
				}
				
				System.out.println("Player 1 Score: " + playerOneScore);
				System.out.println("Player 2 Score: " + playerTwoScore);
				System.out.println("Player 3 Score: " + playerThreeScore);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		System.out.println("\nEnd Total - " + numGames + " Games");
		System.out.println("Player 1 Total: " + playerOneTotal);
		System.out.println("Player 2 Total: " + playerTwoTotal);
		System.out.println("Player 3 Total: " + playerThreeTotal);
		
		keyboard.close();
	}
	
	public static void evaluateScore(boolean[] playerResults, int mainPlayer)
	{
		if (playerResults[0] && playerResults[1])
		{
			if(mainPlayer == 1)
			{
				playerOneScore = 2;
				playerOneTotal = playerOneTotal + 2;
			}
			else if (mainPlayer == 2)
			{
				playerTwoScore = 2;
				playerTwoTotal = playerTwoTotal + 2;
			}
			else if (mainPlayer == 3)
			{
				playerThreeScore = 2;
				playerThreeTotal = playerThreeTotal + 2;
			}
		}
		else if (playerResults[0] || playerResults[1])
		{
			if(mainPlayer == 1)
			{
				playerOneScore = 1;
				playerOneTotal = playerOneTotal + 1;
			}
			else if (mainPlayer == 2)
			{
				playerTwoScore = 1;
				playerTwoTotal = playerTwoTotal + 1;
			}
			else if (mainPlayer == 3)
			{
				playerThreeScore = 1;
				playerThreeTotal = playerThreeTotal + 1;
			}
		}
	}
}
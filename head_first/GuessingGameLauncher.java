class GuessGame {
	// Declaring three player instances
	Player p1;
	Player p2;
	Player p3;
	
	//The method that starts the game
	public void startGame() {
		// Initialising 3 player objects
		p1 = new Player();
		p2 = new Player();
		p3 = new Player();
		
		//Initializing variables to hold players guesses
		int guessp1 = 0;
		int guessp2 = 0;
		int guessp3 = 0;

		//Initializing variables to hold if player is right
		boolean p1isRight = false;
		boolean p2isRight = false;
		boolean p3isRight = false;
		
		// Make a target number the players have to guess
		int targetNumber = (int) (Math.random() * 10);
		System.out.println("I'm thinking of a number between 0 and 9...");
		System.out.println();

		while (true) {
			System.out.println("The number to guess is " + targetNumber);
			System.out.println();
			
			// Make players guessing
			p1.guess();
			p2.guess();
			p3.guess();
			
			System.out.println();

			guessp1 = p1.number;
			System.out.println("Player one guessed " + guessp1);

			guessp2 = p2.number;
			System.out.println("Player two guessed " + guessp2);

			guessp3 = p3.number;
			System.out.println("Player three guessed " + guessp3);

			System.out.println();

			//Check players guesses
			if (guessp1 == targetNumber) {
				p1isRight = true;
			}
			if (guessp2 == targetNumber) {
				p2isRight = true;
			}
			if (guessp3 == targetNumber) {
				p3isRight = true;
			}

			//The winner or try again part
			if (p1isRight || p2isRight || p3isRight) {
				System.out.println("We have a winner!");
				System.out.println("Player one got it right ? " + p1isRight);
				System.out.println("Player two got it right ? " + p2isRight);
				System.out.println("Player three got it right ? " + p3isRight);
				System.out.println("Game is over");
				System.out.println();
				break;
			} else {
				System.out.println("Players will have to try again");
				System.out.println();
			}
		}
	}
}

class Player {
	int number = 0;
	
	public void guess() {
		number = (int) (Math.random() * 10);
		System.out.println("I'm guessing " + number);
	}
}

public class GuessingGameLauncher {
	public static void main(String[] args) {
		GuessGame game = new GuessGame();
		game.startGame();
	}
}

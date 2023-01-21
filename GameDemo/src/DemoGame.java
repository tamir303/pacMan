
public class DemoGame extends Game {

	public DemoGame() {
		GameState welcome = new WelcomeState();
		//GameState play = new PacManGame();
		GameState easy = new PacManGame(3);// for easy level
		GameState medium = new PacManGame(2);// for medium level
		GameState hard= new PacManGame(1);// for hard level
		GameState endGame= new EndGame();

		//stateMachine.installState("Play", play);
		stateMachine.installState("Welcome", welcome);
		stateMachine.installState("Easy", easy);
		stateMachine.installState("Medium", medium);
		stateMachine.installState("Hard", hard);
		stateMachine.installState("EndGame", endGame);
		stateMachine.setStartState(welcome);
	}
	
	public static void main( String[] args ) {
	    Game app = new DemoGame();
	    app.setTitle( "A Simple Game" );
	    app.setVisible( true );
	    app.run();
	    System.exit( 0 );
	  }
}

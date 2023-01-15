import java.util.Random;

public class RandomMachine extends AbstractStateMachine implements GhostStateMachineInterface{

	private final Random random = new Random();

	public RandomMachine(DIRECTION init) {
		super(init);
	}

	public DIRECTION nextState(boolean wallHit, DIRECTION nextDirection) {
		if (!wallHit) {
			return currentState();
		}
		DIRECTION[] directions = DIRECTION.values();
		int randomIndex = random.nextInt(directions.length);
		return directions[randomIndex];
	}
}

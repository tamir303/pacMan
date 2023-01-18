import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomMachine extends AbstractStateMachine implements GhostStateMachineInterface{

	private Random rand;
	
	public RandomMachine(DIRECTION init) {
		super(init);
		rand = new Random();
	}

	public DIRECTION nextState(boolean wallHit, boolean[] canMoveTo) {
		if (!wallHit) 
			return currentState();
		
		// filter for possible direction to move
		List<DIRECTION> moveAbleDirection = Arrays.asList(DIRECTION.values()).stream().filter(direction -> 	
		canMoveTo[direction.getValue()]).collect(Collectors.toList());
		
		// randomly choose the next direction
		DIRECTION nextDirection = moveAbleDirection.get(rand.nextInt(moveAbleDirection.size()));
		setState(nextDirection);
		return nextDirection;
	}

}

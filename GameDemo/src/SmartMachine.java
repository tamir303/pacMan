
public class SmartMachine extends AbstractStateMachine implements GhostStateMachineInterface{

	public SmartMachine(DIRECTION init) {
		super(init);
	}

	public DIRECTION nextState(boolean wallHit, boolean[] canMoveTo, DIRECTION nextDirection) {
		if(!wallHit)
			return currentState();
		setState(nextDirection);
		return nextDirection;
	}
}


public class SmartMachine extends AbstractStateMachine implements GhostStateMachineInterface{

	public SmartMachine(DIRECTION init) {
		super(init);
	}

	
	public DIRECTION nextState(boolean wallHit, DIRECTION nextDirection) {
		if(!wallHit)
			return currentState();
		return nextDirection;
	}
}

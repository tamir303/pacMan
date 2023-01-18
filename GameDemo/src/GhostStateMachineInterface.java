
public interface GhostStateMachineInterface {
	
	public DIRECTION nextState(boolean wallHit, boolean[] canMoveTo);
}

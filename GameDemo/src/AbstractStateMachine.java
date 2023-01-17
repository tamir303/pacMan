
public abstract class AbstractStateMachine {

	private DIRECTION currentState;
	
	
	public AbstractStateMachine (DIRECTION init)
	{
		this.currentState = init;
	}
	
	public DIRECTION currentState()
	{
		return currentState;
	}
	
	public void setState(DIRECTION newState) {
		this.currentState = newState;
	}
	
}
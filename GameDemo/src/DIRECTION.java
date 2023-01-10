
public enum DIRECTION {
	UP(0),
	DOWN(1),
	LEFT(2),
	RIGHT(3),
	STOP(4);
	
	private final int value;
	
	private DIRECTION(final int value) {
		this.value = value;
	}
	
	public int getValue() { return value; }
}


public enum FillType {
	NOT_FILL(0),
	FULL_FILL(1),
	DIAG_LEFT_UP(-1),
	DIAG_RIGHT_DOWN(2),
	DIAG_LEFT_DOWN(-3),
	DIAG_RIGHT_UP(4);
	
	private int code;
	
	private FillType(int code) {
		// TODO Auto-generated constructor stub
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}


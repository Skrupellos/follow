package projects.project7;

import nfa.State;

public class P7State implements State {
	private boolean isFinal = false;
	private boolean isStart = false;
	
	/*package*/ final P7Transitions transitions = new P7Transitions();

	/*package*/ void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	@Override
	public boolean isFinal() {
		return isFinal;
	}

	/*package*/ void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	
	@Override
	public boolean isStart() {
		return isStart;
	}
}

package projects.project7;

import java.util.Iterator;
import java.util.Set;

import nfa.State;

public class P7State implements State {
	private boolean isFinal = false;
	private boolean isStart = false;
	/*package*/ Set<State> states;
	
	/*package*/ final P7Transitions transitions = new P7Transitions();

	/*package*/ void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	/*package*/ Iterator<State> iterator() {
		return states.iterator();
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

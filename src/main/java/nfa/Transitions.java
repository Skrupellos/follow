package nfa;

public interface Transitions {
	/**
	 * @returns null if s is not a valid transition
	 */
	State getStateForCharacter(String s);
}

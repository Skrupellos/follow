package nfa;

public interface TransitionTable extends Iterable<State>{
	Transitions getTransitionsFor(State s);
	State getStart();
}

package projects.project7;

import java.util.Collection;

import com.github.skrupellos.follow.MapHelper;
import com.github.skrupellos.follow.nfa.NfaArrow;
import com.github.skrupellos.follow.nfa.NfaNode;
import com.github.skrupellos.follow.nfa.NfaSymbolArrow;
import com.github.skrupellos.follow.nfa.NfaVisitor;


public class ExportNfa extends MapHelper<NfaNode<String>, P7State> implements NfaVisitor<String> {
	private P7TransitionTable transitionTable = new P7TransitionTable();
	
	
	public static P7TransitionTable apply(NfaNode<String> start) {
		return (new ExportNfa(start)).result();
	}
	
	
	public P7TransitionTable result() {
		return transitionTable;
	}
	
	
	public ExportNfa(NfaNode<String> start) {
		Collection<NfaNode<String>> nodes = start.reachable();
		for(NfaNode<String> node : nodes) {
			node.accept(this);
		}
		
		for(NfaNode<String> node : nodes) {
			for(NfaArrow<String> arrow : node.tails()) {
				arrow.accept(this);
			}
		}
		
		//transitionTable.start = lookup(nfa.start);
	}
	
	
	public void visitArrow(NfaSymbolArrow<String> arrow) {
		P7State tail = lookup(arrow.tail());
		P7State head = lookup(arrow.head());
		tail.transitions.add(arrow.symbol(), head);
	}
	
	
	public void visitNode(NfaNode<String> node) {
		P7State state = new P7State();
		define(node, state);
	}
	
	
	public void visitArrowAll(NfaArrow<String> arrow) {
		throw new RuntimeException("Forgot how to "+arrow);
	}
}

package projects.project7;

import com.github.skrupellos.follow.regex.RegexVisitor;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEmptySet;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;
import com.github.skrupellos.follow.regex.RegexExtNode;
import com.github.skrupellos.follow.nfa.NfaNode;
import com.github.skrupellos.follow.nfa.NfaVisitor;
import com.github.skrupellos.follow.nfa.NfaSymbolArrow;
import com.github.skrupellos.follow.nfa.NfaNode;
import com.github.skrupellos.follow.nfa.NfaArrow;
import com.github.skrupellos.follow.nfa.Nfa;
import com.github.skrupellos.follow.MapHelper;
import regex.RegularExpressionVisitor;
import regex.AlternationExpression;
import regex.Char;
import regex.ConcatenationExpression;
import regex.KleeneStarExpression;
import regex.OptionalExpression;
import regex.PlusExpression;
import regex.RegularExpression;
import java.util.Collection;


public class ExportNfa extends MapHelper<NfaNode<String>, P7State> implements NfaVisitor<String> {
	private P7TransitionTable transitionTable = new P7TransitionTable();
	
	
	public static P7TransitionTable apply(Nfa<String> nfa) {
		return (new ExportNfa(nfa)).result();
	}
	
	
	public P7TransitionTable result() {
		return transitionTable;
	}
	
	
	public ExportNfa(Nfa<String> nfa) {
		Collection<NfaNode<String>> nodes = nfa.start.reachable();
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

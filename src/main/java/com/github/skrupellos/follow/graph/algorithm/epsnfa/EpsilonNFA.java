package com.github.skrupellos.follow.graph.algorithm.epsnfa;

import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.graph.INode;
import com.github.skrupellos.follow.graph.Node;
import com.github.skrupellos.follow.graph.Arrow;

public class EpsilonNFA<ARROW extends Arrow> {

	private INode<ARROW> node;
	private RegexNode regex;

	public EpsilonNFA() {
		this(null);
	}

	public EpsilonNFA(RegexNode regex) {
		this(regex, new Node<ARROW>());
	}

	public EpsilonNFA(RegexNode regex, INode<ARROW> node) {
		this.regex = regex;
		this.node = node;
	}

	public boolean setRegex(RegexNode regex) {
		boolean wasSet = false;
		if(this.regex == null && regex != null) {
			this.regex = regex;
			wasSet = true;
		}
		return wasSet;
	}

	public INode<ARROW> getNode() {
		return node;
	}

	public INode<ARROW> constructFromRegex() {
		if(regex == null) {
			// @todo TODO maybe some more advanced logging?
			StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
			System.out.println("\t[II] There was no regex to construct an epsilonNFA from.");
			System.out.println("\t[II] Caller: " + ste.getClassName() + "\n\t[II] Method: " + ste.getMethodName() + "\n\t[II] Line: " + ste.getLineNumber());
		} else {
			// @todo TODO do some algorithm stuff here
		}
		return node;
	}

	public INode<ARROW> constructFromRegex(RegexNode regex) {
		if(!setRegex(regex)) {
			// @todo TODO maybe some more advanced logging?
			StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
			System.out.println("\t[II] There was no regex given to construct an epsilonNFA from or another is already contained and must not be overriden.");
			System.out.println("\t[II] Caller: " + ste.getClassName() + "\n\t[II] Method: " + ste.getMethodName() + "\n\t[II] Line: " + ste.getLineNumber());
		}
		return constructFromRegex();
	}
}

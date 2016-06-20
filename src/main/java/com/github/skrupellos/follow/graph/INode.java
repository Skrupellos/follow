package com.github.skrupellos.follow.graph;


public interface INode<ARROW extends Arrow> {
	public ArrowSet<ARROW> tails();
	public ArrowSet<ARROW> heads();
	public Node<ARROW> replaceBy(Node<ARROW> replacement);
	public Node<ARROW> takeover(Node<ARROW> node);
}

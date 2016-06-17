package com.github.skrupellos.follow.regex.abstraction;

import java.util.Collections;
import java.util.List;

public abstract class RegexExtNode extends RegexNode {
	public List<RegexNode> getChildren() {
		return Collections.<RegexNode>emptyList();
	}
}

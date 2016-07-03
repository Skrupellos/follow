package com.github.skrupellos.follow.regex;


public interface RegexVisitor<T> {
	default void pre(RegexCatenation<T> regex)   { preInt(regex);   }
	default void pre(RegexStar<T> regex)         { preInt(regex);   }
	default void pre(RegexUnion<T> regex)        { preInt(regex);   }
	default void pre(RegexEmptySet<T> regex)     { preExt(regex);   }
	default void pre(RegexEpsilon<T> regex)      { preExt(regex);   }
	default void pre(RegexSymbol<T> regex)       { preExt(regex);   }
	default void preInt(RegexIntNode<T> regex)   { preAll(regex);   }
	default void preExt(RegexExtNode<T> regex)   { preAll(regex);   }
	default void preAll(RegexNode<T> regex)      {                  }
	
	default void inter(RegexCatenation<T> regex) { interInt(regex); }
	default void inter(RegexUnion<T> regex)      { interInt(regex); }
	default void interInt(RegexIntNode<T> regex) { interAll(regex); }
	default void interAll(RegexNode<T> regex)    {                  }
	
	default void post(RegexCatenation<T> regex)  { postInt(regex);  }
	default void post(RegexStar<T> regex)        { postInt(regex);  }
	default void post(RegexUnion<T> regex)       { postInt(regex);  }
	default void post(RegexEmptySet<T> regex)    { postExt(regex);  }
	default void post(RegexEpsilon<T> regex)     { postExt(regex);  }
	default void post(RegexSymbol<T> regex)      { postExt(regex);  }
	default void postInt(RegexIntNode<T> regex)  { postAll(regex);  }
	default void postExt(RegexExtNode<T> regex)  { postAll(regex);  }
	default void postAll(RegexNode<T> regex)     {                  }
}

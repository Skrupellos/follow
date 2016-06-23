package com.github.skrupellos.follow.regex;


public interface RegexVisitor {
	default void pre(RegexCatenation regex)   { preInt(regex);   }
	default void pre(RegexStar regex)         { preInt(regex);   }
	default void pre(RegexUnion regex)        { preInt(regex);   }
	default void pre(RegexEmptySet regex)     { preExt(regex);   }
	default void pre(RegexEpsilon regex)      { preExt(regex);   }
	default void pre(RegexSymbol regex)       { preExt(regex);   }
	default void preInt(RegexIntNode regex)   { preAll(regex);   }
	default void preExt(RegexExtNode regex)   { preAll(regex);   }
	default void preAll(RegexNode regex)      {                  }
	
	default void inter(RegexCatenation regex) { interInt(regex); }
	default void inter(RegexStar regex)       { interInt(regex); }
	default void inter(RegexUnion regex)      { interInt(regex); }
	default void inter(RegexEmptySet regex)   { interExt(regex); }
	default void inter(RegexEpsilon regex)    { interExt(regex); }
	default void inter(RegexSymbol regex)     { interExt(regex); }
	default void interInt(RegexIntNode regex) { interAll(regex); }
	default void interExt(RegexExtNode regex) { interAll(regex); }
	default void interAll(RegexNode regex)    {                  }
	
	default void post(RegexCatenation regex)  { postInt(regex);  }
	default void post(RegexStar regex)        { postInt(regex);  }
	default void post(RegexUnion regex)       { postInt(regex);  }
	default void post(RegexEmptySet regex)    { postExt(regex);  }
	default void post(RegexEpsilon regex)     { postExt(regex);  }
	default void post(RegexSymbol regex)      { postExt(regex);  }
	default void postInt(RegexIntNode regex)  { postAll(regex);  }
	default void postExt(RegexExtNode regex)  { postAll(regex);  }
	default void postAll(RegexNode regex)     {                  }
}

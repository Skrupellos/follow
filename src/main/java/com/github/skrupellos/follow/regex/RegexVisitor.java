/* This file is part of Follow (https://github.com/Skrupellos/follow).
 * Copyright (c) 2016 Skruppy <skruppy@onmars.eu> and kratl.
 *
 * Follow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Follow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Follow. If not, see <http://www.gnu.org/licenses/>.
 */

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

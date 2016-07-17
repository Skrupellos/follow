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

package projects.project7;

import com.github.skrupellos.follow.follow.AlgorithmBase;
import com.github.skrupellos.follow.regex.RegexCatenation;
import com.github.skrupellos.follow.regex.RegexEpsilon;
import com.github.skrupellos.follow.regex.RegexNode;
import com.github.skrupellos.follow.regex.RegexStar;
import com.github.skrupellos.follow.regex.RegexSymbol;
import com.github.skrupellos.follow.regex.RegexUnion;

import regex.AlternationExpression;
import regex.Char;
import regex.ConcatenationExpression;
import regex.KleeneStarExpression;
import regex.OptionalExpression;
import regex.PlusExpression;
import regex.RegularExpression;
import regex.RegularExpressionVisitor;


public class ImportRegex extends AlgorithmBase<RegularExpression, RegexNode<String>> implements RegularExpressionVisitor {
	public static RegexNode<String> apply(RegularExpression root) {
		return (new ImportRegex(root)).result();
	}
	
	
	public ImportRegex(RegularExpression root) {
		super(root);
		root.accept(this);
	}
	
	
	public void post(AlternationExpression expression){
		define(expression, new RegexUnion<String>(
			lookup(expression.getLHS()),
			lookup(expression.getRHS())
		));
	}
	
	
	public void visit(Char character) {
		define(character, new RegexSymbol<String>(character.getCharacter()));
	}
	
	
	public void post(ConcatenationExpression expression){
		define(expression, new RegexCatenation<String>(
			lookup(expression.getLHS()),
			lookup(expression.getRHS())
		));
	}
	
	
	public void post(KleeneStarExpression expression) {
		define(expression, new RegexStar<String>(
			lookup(expression.getChild())
		));
	}
	
	
	public void post(OptionalExpression expression) {
		define(expression, new RegexUnion<String>(
			new RegexEpsilon<String>(),
			lookup(expression.getChild())
		));
	}
	
	
	public void post(PlusExpression expression) {
		RegexNode<String> child = lookup(expression.getChild());
		define(expression, new RegexCatenation<String>(
			child,
			new RegexStar<String>(
				child.deepCopy()
			)
		));
	}
	
	
	public void post(RegularExpression expression) {
		throw new RuntimeException("Forgot how to "+expression);
	}
}

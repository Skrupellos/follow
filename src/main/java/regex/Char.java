package regex;

public interface Char extends RegularExpression {
	@Override
	default void accept(RegularExpressionVisitor e) {
		e.visit(this);		
	}
	String getCharacter();
}

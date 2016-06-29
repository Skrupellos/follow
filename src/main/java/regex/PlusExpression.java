package regex;

public interface PlusExpression extends UnaryExpression {
	@Override
	default void accept(RegularExpressionVisitor e) {
		e.pre(this);
		getChild().accept(e);
		e.post(this);
	}
}

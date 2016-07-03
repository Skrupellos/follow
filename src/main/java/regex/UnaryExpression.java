package regex;

public interface UnaryExpression extends RegularExpression {
	@Override
	default void accept(RegularExpressionVisitor e) {
		e.pre(this);
		getChild().accept(e);
		e.post(this);
	}
	RegularExpression getChild();
}

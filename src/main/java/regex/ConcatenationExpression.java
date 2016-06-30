package regex;

public interface ConcatenationExpression extends BinaryExpression {
	@Override
	default void accept(RegularExpressionVisitor e) {
		e.pre(this);
		getLHS().accept(e);
		getRHS().accept(e);
		e.post(this);
	}
}

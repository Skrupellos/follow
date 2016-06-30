package regex;

public interface BinaryExpression extends RegularExpression {
	@Override
	default void accept(RegularExpressionVisitor e) {
		e.pre(this);
		getLHS().accept(e);
		getRHS().accept(e);
		e.post(this);
	}
	RegularExpression getLHS();
	RegularExpression getRHS();
}

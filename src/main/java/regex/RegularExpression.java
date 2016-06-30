package regex;

public interface RegularExpression {
	void accept(RegularExpressionVisitor e);
}

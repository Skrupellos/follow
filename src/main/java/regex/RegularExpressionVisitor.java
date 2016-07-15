package regex;

public interface RegularExpressionVisitor {

	default void pre(BinaryExpression expression) {
		pre((RegularExpression)expression);
	}
	default void post(BinaryExpression expression){
		post((RegularExpression)expression);
	}
	default void pre(ConcatenationExpression expression) {
		pre((BinaryExpression)expression);
	}
	default void post(ConcatenationExpression expression){
		post((BinaryExpression)expression);
	}
	default void pre(AlternationExpression expression) {
		pre((BinaryExpression)expression);
	}
	default void post(AlternationExpression expression){
		post((BinaryExpression)expression);
	}
	default void post(OptionalExpression expression) {
		post((UnaryExpression)expression);
	};
	default void pre(OptionalExpression expression) {
		pre((UnaryExpression)expression);
	};
	default void post(KleeneStarExpression expression) {
		post((UnaryExpression)expression);
	};
	default void pre(KleeneStarExpression expression) {
		pre((UnaryExpression)expression);
	};
	default void post(PlusExpression expression) {
		post((UnaryExpression)expression);
	};
	default void pre(PlusExpression expression) {
		pre((UnaryExpression)expression);
	};
	default void post(UnaryExpression expression) {
		post((RegularExpression)expression);
	};
	default void pre(UnaryExpression expression) {
		pre((RegularExpression)expression);
	};
	
	default void visit(Char character) {};
	default void pre(RegularExpression expression) {};
	default void post(RegularExpression expression) {};

}

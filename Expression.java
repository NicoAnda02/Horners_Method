package mainPK;

public class Expression {
	
	private Float coefficient;
	private int exponent;

	public Expression() {
		
	}
	
	public Expression(Float coef, int exp) {
		coefficient = coef;
		exponent = exp;
	}
	
	public Expression(Expression expression) {
		this.coefficient = expression.coefficient;
		this.exponent = expression.exponent;
	}
	
	public void setCoefficient(Float coef) {
		coefficient = coef;
	}
	
	public void setExponent(int exp) {
		exponent = exp;
	}
	
	public Float getCoefficient() {
		return coefficient;
	}
	
	public int getExponent() {
		return exponent;
	}
	
	public String display() {
		String polynomial;
		if (exponent != 0)
			polynomial = coefficient + "x^" + exponent;
		else
			polynomial = "" + coefficient;
		return polynomial;
	}
	
	public int compare(Expression otherExp) {
		if (this.exponent > otherExp.exponent) //indicates this exponent is larger
			return 0;
		else if (this.exponent > otherExp.exponent) //indicates this exponent is smaller
			return 1;
		else return 2; //indicates both exponents are the same
	}
	
}

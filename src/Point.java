import java.math.BigInteger;
import java.util.Objects;

import lombok.Getter;

@Getter
public class Point {
	private FieldElement x;
	private FieldElement y;
	private FieldElement a;
	private FieldElement b;

	public Point(FieldElement x, FieldElement y, FieldElement a, FieldElement b) {
		this.x = x;
		this.y = y;
		this.a = a;
		this.b = b;

		if (Objects.isNull(this.x) && Objects.isNull(this.y)) {
			return;
		}
		if (!this.y.pow(new BigInteger("2"))
				.equals(this.x.pow(new BigInteger("3")).add(this.a.mul(this.x)).add(this.b))) {
			throw new IllegalArgumentException(
					String.format("x=%s, y=%s is not on the curve.", this.x.toString(), this.y.toString()));
		}
	}

	public boolean equals(Point other) {
		return x.equals(other.x) && y.equals(other.y) && a.equals(other.a) && b.equals(other.b);
	}

	public Point add(Point other) {
		if (!a.equals(other.a) || !b.equals(other.b)) {
			throw new IllegalArgumentException(
					String.format("Points %s, %s are not on the same curve.", this.toString(), other.toString()));
		}
		if (Objects.isNull(this.x)) {
			return other;
		}
		if (Objects.isNull(other.x)) {
			return this;
		}
		if (x.equals(other.x) && !y.equals(other.y)) {
			return new Point(null, null, a, b);
		}
		if (!x.equals(other.x)) {
			final FieldElement s = other.y.sub(y).div(other.x.sub(x));
			final FieldElement tx = s.pow(new BigInteger("2")).sub(x).sub(other.x);
			final FieldElement ty = s.mul(x.sub(tx)).sub(y);
			return new Point(tx, ty, a, b);
		}
		if (this.equals(other) && y.equals(new FieldElement(BigInteger.ZERO, x.getPrime()).mul(x))) {
			return new Point(null, null, a, b);
		}
		if (this.equals(other)) {
			final FieldElement s = (new FieldElement(new BigInteger("3"), x.getPrime()).mul(x.pow(new BigInteger("2")))
					.add(a)).div(new FieldElement(new BigInteger("2"), x.getPrime()).mul(y));
			final FieldElement tx = s.pow(new BigInteger("2")).sub(new FieldElement(new BigInteger("2"), x.getPrime()).mul(x));
			final FieldElement ty = s.mul(x.sub(tx)).sub(y);
			return new Point(tx, ty, a, b);
		}
		return null;
	}
	
	public Point rmul(BigInteger coefficient) {
		BigInteger coef = coefficient;
		Point current = this;
		Point result = new Point(null, null, a, b);
		while (coef.compareTo(BigInteger.ZERO) > 0) {
			if (coef.testBit(0)) {
				result = result.add(current);
			}
			current = current.add(current);
			coef = coef.shiftRight(1);
		}
		return result;
	}
}

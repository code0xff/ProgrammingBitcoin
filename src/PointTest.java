import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class PointTest {

	@Test
	void scalaMultiflyTest() {
		final BigInteger prime = new BigInteger("223");
		final FieldElement a = new FieldElement(BigInteger.ZERO, prime);
		final FieldElement b = new FieldElement(new BigInteger("7"), prime);
		final FieldElement x = new FieldElement(new BigInteger("47"), prime);
		final FieldElement y = new FieldElement(new BigInteger("71"), prime);

		final Point p = new Point(x, y, a, b);
		for (int i = 1; i <= 20; i++) {
			final Point tp = p.rmul(new BigInteger(String.valueOf(i)));
			System.out.printf("x=%s y=%s\n", tp.getX().getNum().toString(), tp.getY().getNum().toString());
		}
	}

}

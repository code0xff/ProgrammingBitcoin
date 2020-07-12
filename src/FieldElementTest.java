import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class FieldElementTest {

	@Test
	void createFieldElementTest() {
		final FieldElement a = new FieldElement(new BigInteger("7"), new BigInteger("13"));
		final FieldElement b = new FieldElement(new BigInteger("6"), new BigInteger("13"));
		
		assertFalse(a.equals(b));
		assertTrue(a.equals(a));
	}
	
	@Test
	void addFieldElementTest() {
		final FieldElement a = new FieldElement(new BigInteger("7"), new BigInteger("13"));
		final FieldElement b = new FieldElement(new BigInteger("12"), new BigInteger("13"));
		final FieldElement c = new FieldElement(new BigInteger("6"), new BigInteger("13"));
		
		assertTrue(a.add(b).equals(c));		
	}
}

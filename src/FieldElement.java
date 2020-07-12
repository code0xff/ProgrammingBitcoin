import java.math.BigInteger;
import java.util.Objects;

import lombok.Getter;

@Getter
public class FieldElement {
	private BigInteger num;
	private BigInteger prime;

	public FieldElement(BigInteger num, BigInteger prime) {
		if (num.compareTo(prime) >= 0 || num.compareTo(BigInteger.ZERO) < 0) {
			throw new IllegalArgumentException(String.format("Num %s not in field range 0 to %s", num.toString(),
					prime.subtract(BigInteger.ONE).toString()));
		}
		this.num = num;
		this.prime = prime;
	}

	@Override
	public String toString() {
		return String.format("FieldElement prime=%s, num=%s", prime.toString(), num.toString());
	}

	public boolean equals(FieldElement other) {
		if (Objects.isNull(other)) {
			return false;
		}
		return num.equals(other.getNum()) && prime.equals(other.getPrime());
	}

	public FieldElement add(FieldElement other) {
		if (!prime.equals(other.getPrime())) {
			throw new IllegalArgumentException("Cannot add two numbers in different Fields.");
		}
		return new FieldElement(num.add(other.getNum()).mod(prime), prime);
	}

	public FieldElement sub(FieldElement other) {
		if (!prime.equals(other.getPrime())) {
			throw new IllegalArgumentException("Cannot subtract two numbers in different Fields.");
		}
		return new FieldElement(num.subtract(other.getNum()).mod(prime), prime);
	}

	public FieldElement mul(FieldElement other) {
		if (!prime.equals(other.getPrime())) {
			throw new IllegalArgumentException("Cannot multiply two numbers in different Fields.");
		}
		return new FieldElement(num.multiply(other.getNum()).mod(prime), prime);
	}

	public FieldElement pow(BigInteger exponent) {
		final BigInteger n = exponent.mod(prime.subtract(BigInteger.ONE));
		return new FieldElement(num.modPow(n, prime), prime);
	}

	public FieldElement div(FieldElement other) {
		if (!prime.equals(other.getPrime())) {
			throw new IllegalArgumentException("Cannot divide two numbers in different Fields.");
		}
		return new FieldElement(
				num.multiply(other.getNum().modPow(prime.subtract(new BigInteger("2")), prime)).mod(prime), prime);
	}
}

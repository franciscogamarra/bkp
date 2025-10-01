package br.utils.lambda;

@FunctionalInterface
public interface F2<I1, I2, OUT> {
	OUT call(I1 i1, I2 i2);
}
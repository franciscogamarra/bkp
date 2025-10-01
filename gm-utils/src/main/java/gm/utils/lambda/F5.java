package gm.utils.lambda;

@FunctionalInterface
public interface F5<I1, I2, I3, I4, I5, OUT> {
	OUT call(I1 i1, I2 i2, I3 i3, I4 i4, I5 i5);
}

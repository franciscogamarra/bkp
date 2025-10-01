package gm.utils.lambda;

@FunctionalInterface
public interface F4<I1, I2, I3, I4, OUT> {
	OUT call(I1 i1, I2 i2, I3 i3, I4 i4);
}

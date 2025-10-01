package gm.utils.lambda;

@FunctionalInterface
public interface F3<I1, I2, I3, OUT> {
	OUT call(I1 i1, I2 i2, I3 i3);
}

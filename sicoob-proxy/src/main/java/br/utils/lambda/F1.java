package br.utils.lambda;

@FunctionalInterface
public interface F1<I1, OUT> {
	OUT call(I1 i1);
}
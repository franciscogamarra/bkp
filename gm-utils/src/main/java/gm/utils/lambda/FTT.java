package gm.utils.lambda;

@FunctionalInterface @Deprecated /* usar F1 */
public interface FTT<OUTPUT, INPUT> {
	OUTPUT call(INPUT o);
}
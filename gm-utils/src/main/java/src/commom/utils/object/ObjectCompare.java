package src.commom.utils.object;

public class ObjectCompare {

	public static boolean eq(Object a, Object b) {
		return Equals.deep(a, b, true, true);
	}
	
}

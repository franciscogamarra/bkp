package gm.utils.comum;

/* utilizado na classe QueryOperator para converter um tipo em um valor do banco */
public interface IWrapper<T> {
	T unwrapper();
}
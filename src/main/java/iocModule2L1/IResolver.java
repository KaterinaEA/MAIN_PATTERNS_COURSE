package iocModule2L1;

public interface IResolver<T> {

    // параметризированный метод
    T resolve(String d, Object... args);
}

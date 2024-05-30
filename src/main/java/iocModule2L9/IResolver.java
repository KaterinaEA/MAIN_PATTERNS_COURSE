package iocModule2L9;

public interface IResolver<T> {

    // параметризированный метод
    T resolve(String d, Object... args);
}

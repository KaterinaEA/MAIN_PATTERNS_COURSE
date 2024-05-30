package iocModule2L9.Scope;

public interface IDependencyResolver<T> {

    T resolve(String dependency, Object... args);

}

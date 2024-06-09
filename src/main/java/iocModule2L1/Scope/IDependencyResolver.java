package iocModule2L1.Scope;

public interface IDependencyResolver<T> {

    T resolve(String dependency, Object... args);

}

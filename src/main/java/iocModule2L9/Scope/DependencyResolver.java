package iocModule2L9.Scope;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.function.Function;

@RequiredArgsConstructor
public class DependencyResolver implements IDependencyResolver{

    HashMap<String, Function<String, Object>> _dependencies;

    @Override
    public Object resolve(String dependency, Object... args) {

        HashMap<String, Function<String, Object>> dependencies = _dependencies;

        while(true) {

            Function<String, Object> dependencyResolverStrategy = dependencies.get(dependency);

            if (dependencies.containsKey(dependency)) {
                return dependencyResolverStrategy.apply(dependency);
            }
        }

    }
}

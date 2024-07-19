package iocModule2L1.Scope;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.function.Function;

@RequiredArgsConstructor
public class DependencyResolver implements IDependencyResolver{

    HashMap<String, Function<Object[], Object>> _dependencies;

    public DependencyResolver (HashMap<String, Function<Object[], Object>> dependency ) {
        _dependencies = dependency;
    }

    @Override
    public Object resolve(String dependency, Object... args) {

        HashMap<String, Function<Object[], Object>> dependencies = _dependencies;


            Function<Object[], Object> dependencyResolverStrategy = dependencies.get(dependency);

            if (dependencies.containsKey(dependency)) {
                return dependencyResolverStrategy.apply(args);
            }
            else {
                dependencies = InitCommand.rootScope;
                dependencyResolverStrategy = dependencies.get(dependency);
                if (dependencies.containsKey(dependency)) {
                    return dependencyResolverStrategy.apply(args);
                }
            }
        throw new IllegalArgumentException(String.format("Dependency %s is not found.", dependency));

    }
}

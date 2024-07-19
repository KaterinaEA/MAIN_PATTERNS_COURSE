package iocAdapterBridgeModule2L12;

import javax.tools.*;
import java.util.Hashtable;
import java.util.Map;

public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private Map<String, JavaClassAsBytes> compiledClasses;

    public InMemoryFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
        this.compiledClasses = new Hashtable<>();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
                                               String className, JavaFileObject.Kind kind, FileObject sibling) {

        JavaClassAsBytes classAsBytes = new JavaClassAsBytes(className, kind);
        compiledClasses.put(className, classAsBytes);

        return classAsBytes;
    }

    public Map<String, JavaClassAsBytes> getBytesMap() {
        return compiledClasses;
    }
}

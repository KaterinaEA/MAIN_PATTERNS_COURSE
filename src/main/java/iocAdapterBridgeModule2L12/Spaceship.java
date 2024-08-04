package iocAdapterBridgeModule2L12;

import java.util.HashMap;
import java.util.Map;

public class Spaceship implements Uobject{
    public static final Map<String, Object> dictionaryProperty = new HashMap<>();

    @Override
    public Object getProperty(String key) {

        //System.out.println(String.format("Running getProperties %s Spaceship", key));
        return dictionaryProperty.get(key);

    }

    @Override
    public void setProperty(String key, Object value) {

        dictionaryProperty.put(key, value);
        //System.out.println(String.format("Running setProperties %s=%s Spaceship", key, value.toString()));

    }
}

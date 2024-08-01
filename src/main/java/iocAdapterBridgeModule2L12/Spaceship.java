package iocAdapterBridgeModule2L12;

import iocModule2L1.IoC;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Spaceship implements Uobject{
    public static final Map<String, Function<Object[], Object>> dictionaryProperty = new HashMap<>();

    public Spaceship (){
        int[] init_velocity = {0, 0};
        dictionaryProperty.put("position", IoC.resolve("register","Vector", init_velocity ));
    }
    @Override
    public Object getProperty(String key) {
        return null;
    }

    @Override
    public void setProperty(String key, Object value) {

    }
}

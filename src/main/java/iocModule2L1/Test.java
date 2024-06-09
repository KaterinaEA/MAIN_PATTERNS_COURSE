package iocModule2L1;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Test {

    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<>();

        Function<String, String> function  = (s) -> s + s;
        BiFunction<String, String, String> biFunction= (a, b) -> a + b;

        map.put("a", 123);
        map.put("b", "123");
        map.put("c", function);
        map.put("d", biFunction);

        System.out.println(function.getClass().getName());
        System.out.println(biFunction.getClass().getName());

        System.out.println(map.get("a").getClass().getName());
        System.out.println(map.get("b").getClass().getName());
        System.out.println(map.get("c").getClass().getName());
        System.out.println(map.get("d").getClass().getName());

        System.out.println(((Function<String, String>) map.get("c")).apply("123"));

    }

}
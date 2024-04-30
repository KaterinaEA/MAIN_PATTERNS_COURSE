import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // Создаем два массива
        int[] array1 = {1, 2, 3};
        int[] array2 = {4, 5, 6};
        int[] result_mass = new int[array1.length];

        for (int i = 0; i < array1.length; i++) {
            result_mass[i] = array1[i] + array2[i];
        }

    }


}

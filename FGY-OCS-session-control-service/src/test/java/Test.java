import java.util.HashMap;
import java.util.Random;

/**
 * @author fgy
 * description
 * date 2023/6/1 11:19
 */
public class Test {
    public static void main(String[] args) {
        HashMap<Integer, Long> map = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            map.put(i,System.currentTimeMillis() - random.nextInt(10000));
        }
        long start = System.currentTimeMillis();
        map.entrySet().stream().filter(e -> {
            Long value = e.getValue();
            return value  > System.currentTimeMillis() - 20 * 1000;
        }).forEach(entry -> {
            Integer key = entry.getKey();
            System.out.println(key);
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}


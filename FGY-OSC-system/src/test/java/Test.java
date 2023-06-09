/**
 * @author fgy
 * description
 * date 2023/6/6 15:02
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    break;
                }
                System.out.println(i + " " + j);
            }
        }
    }
}

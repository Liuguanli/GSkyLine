import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 2017/11/2.
 */
public class Test {

    public static void main(String[] args) {
        for (int i = 1; i < 0; i++) {
            System.out.print("dfd");

        }

        List<Integer> one = new ArrayList<>();
        one.add(1);
        List<Integer> two = new ArrayList<>();
        two.add(1);
        two.add(2);
        one.addAll(two);



        System.out.print(one);
//        two.remove(0);
//        System.out.print(two);


    }

}

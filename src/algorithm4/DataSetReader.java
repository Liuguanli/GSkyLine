package algorithm4;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 2017/11/1.
 */
public class DataSetReader {

    public List<Point> read(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer("");
        FileReader reader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(reader);

        String str = null;
        List<Point> dataItems = new LinkedList<>();
        int index = 0;
        while ((str = br.readLine()) != null) {
//            sb.append(str+"/n");
//            System.out.println(str);
            String[] args = str.split(" ");
            Point point = new Point(args, index++);
            dataItems.add(point);
        }

        br.close();
        reader.close();
        return dataItems;
    }
}

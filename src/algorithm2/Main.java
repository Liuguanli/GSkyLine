package algorithm2;

import utils.Constants;
import utils.PropertiesHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * Created by apple on 2017/11/3.
 */
public class Main {
    public static void main(String[] args) {
        GSkyLine2 gSkyLine2 = new GSkyLine2();
        DataSetReader reader = new DataSetReader();
        try {
            long begin = System.currentTimeMillis();
            String filePath = PropertiesHelper.properties.get(Constants.FILE_PATH);
            List<Point> dataItems = reader.read(filePath);
            long end = System.currentTimeMillis();
            System.out.println("读数据耗时:" + (end - begin));
            Collections.sort(dataItems);
            for (int i = 0; i < dataItems.size(); i++) {
                dataItems.get(i).setIndex(i);
            }
            begin = System.currentTimeMillis();
            List<algorithm2.Layer> layers = null;
            if (filePath.split("//.")[0].split("_")[1].equals("2")) {
                layers = gSkyLine2.gSkyLine(dataItems);
            } else {
                layers = gSkyLine2.gSkyLineforMultiDimen(dataItems);
            }
            end = System.currentTimeMillis();
            System.out.println("gSkyLine耗时:" + (end - begin));
            begin = System.currentTimeMillis();
            gSkyLine2.dsg(layers);
            end = System.currentTimeMillis();
            System.out.println("dsg耗时:" + (end - begin));

            begin = System.currentTimeMillis();
            int k = Integer.valueOf(PropertiesHelper.properties.get(Constants.K));
            List<Group> groups = gSkyLine2.pointWise(dataItems, k);
            end = System.currentTimeMillis();
            System.out.println("pointWise耗时:" + (end - begin));
            System.out.println("group大小：" + groups.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }
}

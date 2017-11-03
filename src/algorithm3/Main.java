package algorithm3;

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
        GSkyLine3 gSkyLine3 = new GSkyLine3();
        DataSetReader reader = new DataSetReader();
        try {

            long begin = System.currentTimeMillis();
            String filePath = PropertiesHelper.properties.get(Constants.FILE_PATH);
            List<Point> dataItems = reader.read(filePath);
            long end = System.currentTimeMillis();
            System.out.println("读数据耗时:" + (end - begin));
            Collections.sort(dataItems);
            begin = System.currentTimeMillis();
            List<Layer> layers = null;
            if (filePath.split("//.")[0].split("_")[1].equals("2")) {
                layers = gSkyLine3.gSkyLine(dataItems);
            } else {
                layers = gSkyLine3.gSkyLineforMultiDimen(dataItems);
            }
            System.out.println("layer1 point number:" + layers.get(0).getLayerPoints().size());
            end = System.currentTimeMillis();
            System.out.println("gSkyLine耗时:" + (end - begin));
            begin = System.currentTimeMillis();
            gSkyLine3.dsg(layers);
            end = System.currentTimeMillis();
            System.out.println("dsg耗时:" + (end - begin));
            int k = 3;
            begin = System.currentTimeMillis();
            List<Group> groups = gSkyLine3.unitgroupwise(dataItems, k);
            end = System.currentTimeMillis();
            System.out.println("unitgroupwise耗时:" + (end - begin));
            System.out.println("group大小：" + groups.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }



}

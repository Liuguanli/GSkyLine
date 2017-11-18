package algorithm1;

import utils.Constants;
import utils.PropertiesHelper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by apple on 2017/11/3.
 * <p>
 * Algorithm 1 of 《Finding Pareto Optimal Groups: Group-based Skyline》
 * input: a set of n points in two dimensional space.
 * output: l skyline layers.
 * <p>
 * ps: we can support n dimensional space
 */
public class Main {
    public static void main(String[] args) {
        DataSetReader reader = new DataSetReader();
        try {
            String filePath = PropertiesHelper.properties.get(Constants.FILE_PATH);
            List<Point> dataItems = reader.read(filePath);
            Collections.sort(dataItems);
            GSkyLine gSkyLine = new GSkyLine();
            long begin = System.currentTimeMillis();
            List<Layer> layers = gSkyLine.gSkyLine(dataItems);
            long end = System.currentTimeMillis();
            System.out.println("gSkyLine耗时:" + (end - begin));
            printLayerPointsNum(layers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }

    public static void printLayerPointsNum(List<Layer> layers) {
        for (int i = 0; i < layers.size(); i++) {
            System.out.println("第" + i + "层的点的数量是:" + layers.get(i).getLayerPoints().size());
        }

    }
}

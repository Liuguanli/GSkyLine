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
 *
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
            List<Layer> layers = gSkyLine.gSkyLine(dataItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }
}

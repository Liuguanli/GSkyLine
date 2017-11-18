package algorithm5;

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
            for (int i = 0; i < dataItems.size(); i++) {
                dataItems.get(i).setIndex(i);
            }
            begin = System.currentTimeMillis();
            List<Layer> layers = null;
            if (filePath.split("//.")[0].split("_")[1].equals("2")) {
                layers = gSkyLine3.gSkyLine(dataItems);
            } else {
                layers = gSkyLine3.gSkyLineforMultiDimen(dataItems);
            }
            testgSkyLine(layers);
            System.out.println("layer1 point number:" + layers.get(0).getLayerPoints().size());
            System.out.println("layer2 point number:" + layers.get(1).getLayerPoints().size());
            System.out.println("layer(0):" + layers.get(0));
            System.out.println("layer(1):" + layers.get(1));
            end = System.currentTimeMillis();
            System.out.println("gSkyLine耗时:" + (end - begin));
            begin = System.currentTimeMillis();
            gSkyLine3.dsg(layers);
            test(dataItems);
            end = System.currentTimeMillis();
            System.out.println("dsg耗时:" + (end - begin));
            int k = Integer.valueOf(PropertiesHelper.properties.get(Constants.K));
            begin = System.currentTimeMillis();
            List<Group> groups = gSkyLine3.unitgroupwise(dataItems, k);
            end = System.currentTimeMillis();
            System.out.println("unitgroupwise耗时:" + (end - begin));
            System.out.println("group大小：" + groups.size());
            for (int i = 0; i < groups.size(); i++) {
                System.out.println(groups.get(i));
            }

//            for (int i = 0; i < groups.size(); i++) {
//                Group group = groups.get(i);
//                List<Point> points = group.getPoints();
//                System.out.println("***************************");
//                for (int j = 0; j < points.size(); j++) {
//                    Point point = points.get(j);
//                    if (layers.get(0).getLayerPoints().contains(point)) {
//                        System.err.println("第" + i + "个结果中的第 " + j + " 点来自第0层");
//                    } else if (layers.get(1).getLayerPoints().contains(point)) {
//                        System.err.println("第" + i + "个结果中的第 " + j + " 点来自第1层");
//                    } else {
//                        System.err.println("第" + i + "个结果中的第 " + j + " 点来自层" + point.getLayer());
//                    }
//
//                }
//            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }

    public static void test(List<Point> points) {
        System.err.println("test" + points.size());
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
//            if ((Math.abs(point.getDimentionalData()[0] - 167.9396f)) < 0.00001f && (Math.abs(point.getDimentionalData()[1] - 0.452f)) < 0.00001f) {
//                System.err.println("parent " + point);
//                System.err.println("parent num " + point.getParentsIndex().size());
//                System.err.println("layer num " + point.getLayer());
////                for (int j = 0; j < point.getParentsIndex().size(); j++) {
////                    System.err.println("parent " + j + " :" + points.get(point.getParentsIndex().get(j)));
////                }
//            }
        }
    }

    // 验证layer建立有没有问题
    public static void testgSkyLine(List<Layer> layers) {
        System.err.println("testgSkyLine");
        for (int i = 0; i < layers.size() - 1; i++) {
            Layer lowLayer = layers.get(i);
            for (int j = i + 1; j < layers.size(); j++) {
                Layer highLayer = layers.get(j);
                for (int l = 0; l < highLayer.getLayerPoints().size(); l++) {
                    if (!lowLayer.isDominate(highLayer.getLayerPoints().get(l))) {
                        System.err.println("highLayer Point:" + highLayer.getLayerPoints().get(l));
                    }
                }
            }
        }
    }


}

package algorithm2;

import utils.Constants;
import utils.PropertiesHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
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
            List<Layer> layers = null;
            if (filePath.split("//.")[0].split("_")[1].equals("2")) {
                layers = gSkyLine2.gSkyLine(dataItems);
            } else {
                layers = gSkyLine2.gSkyLineforMultiDimen(dataItems);
            }
            System.out.println("layer1 point number:" + layers.get(0).getLayerPoints().size());
            System.out.println("layer2 point number:" + layers.get(1).getLayerPoints().size());
            System.out.println("layer(0):" + layers.get(0));
            System.out.println("layer(1):" + layers.get(1));


            end = System.currentTimeMillis();
            System.out.println("gSkyLine耗时:" + (end - begin));
            begin = System.currentTimeMillis();
            gSkyLine2.dsg(layers);
            end = System.currentTimeMillis();
            System.out.println("dsg耗时:" + (end - begin));
//            test(dataItems);
            begin = System.currentTimeMillis();
            int k = Integer.valueOf(PropertiesHelper.properties.get(Constants.K));
            List<Group> groups = gSkyLine2.pointWise(dataItems, k);
            end = System.currentTimeMillis();
            System.out.println("pointWise耗时:" + (end - begin));
            System.out.println("group大小：" + groups.size());
//            for (int i = 0; i < groups.size(); i++) {
//                System.out.println(groups.get(i));
//            }

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
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1.getChildrenIndex().size() > o2.getChildrenIndex().size()) {
                    return 1;
                } else if (o1.getChildrenIndex().size() < o2.getChildrenIndex().size()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            System.out.println("第" + i + "个点的孩子数量：" + point.getChildrenIndex().size() + " 父亲数量：" + point.getParentsIndex().size());
//            if (point.getDimentionalData()[0] == 167.9396F && point.getDimentionalData()[1] == 0.452F) {
//                for (int j = 0; j < point.getParentsIndex().size(); j++) {
//                    System.out.println("parent " + j + " :" + points.get(point.getParentsIndex().get(j)));
//                }
//            }
        }
    }

}

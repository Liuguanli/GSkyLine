package multi_dimension_dsg;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 2017/11/1.
 */
public class GSkyLine2 {
    public static void main(String[] args) {
        DataSetReader reader = new DataSetReader();
        try {
            List<Point> dataItems = reader.read("datasets/anti_8.txt");
            System.out.println(dataItems.size());
            Collections.sort(dataItems);
//            for (Point point : dataItems) {
//                System.out.println(point);
//            }
            List<Layer> layers = gSkyLine(dataItems);
            dsg(layers);
            for (Point point : dataItems) {
                System.out.println(point);
            }

//            int sum = 0;
//            for (Layer layer : layers) {
//                sum += layer.getLayerPoints().size();
//                System.out.println(layer);
//                break;
//            }
//            System.out.println(sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }

    public static List<Layer> gSkyLine(List<Point> points) {
        Point point1 = points.get(0);
        point1.setLayer(0);
        int maxLayerNum = 0;
        Layer layer1 = new Layer();
        layer1.getLayerPoints().add(point1);
        layer1.setTailPoint(point1);
        layer1.setLayerNum(0);
        List<Layer> layers = new LinkedList<>();
        layers.add(layer1);
        for (int i = 1; i < points.size(); i++) {
            Point p_u_i = points.get(i);
            if (!layer1.isDominate(p_u_i)) {
                p_u_i.setLayer(0);
                layer1.setTailPoint(p_u_i);
            } else if (layers.get(layers.size() - 1).isDominate(p_u_i)) {
                p_u_i.setLayer(++maxLayerNum);
                Layer newLayer = new Layer();
                newLayer.setTailPoint(p_u_i);
                newLayer.setLayerNum(maxLayerNum);
                layers.add(newLayer);
            } else {
                int min = 0;
                int max = maxLayerNum;
                while (min < max) {
                    int middle = (max + min) / 2;
                    if (middle + 1 < layers.size()) {
                        boolean isLeftDominate = layers.get(middle).isDominate(p_u_i);
                        boolean isRightDominate = layers.get(middle + 1).isDominate(p_u_i);
                        if (!isRightDominate && isLeftDominate) {
                            p_u_i.setLayer(middle + 1);
                            layers.get(middle + 1).setTailPoint(p_u_i);
                            break;
                        } else if (isLeftDominate && isRightDominate) {
                            min = middle + 1;
                        } else if (!isLeftDominate && !isRightDominate) {
                            max = middle;
                        }
                    }
                }
            }
            layers.get(p_u_i.getLayer()).getLayerPoints().add(p_u_i);
        }
        return layers;
    }

    public static void dsg(List<Layer> layers) {
        if (layers == null || layers.size() < 2)
            return;
        int index = 0;
        for (int i = 1; i < layers.size(); i++) {
            Layer outer = layers.get(i);
            List<Point> outerPoints = outer.getLayerPoints();
            for (int j = 0; j < i; j++) {
                Layer inner = layers.get(j);
                List<Point> innerPoints = inner.getLayerPoints();
                buildRelation(innerPoints,outerPoints);
//
//                for (Point innerPoint : innerPoints) {
//                    for (Point outerPoint : outerPoints) {
//                        if (innerPoint.isDominate(outerPoint)) {
//                            System.out.println("isDominate");
//                            innerPoint.getChildrenIndex().add(outerPoint.getIndex());
//                            outerPoint.getParentsIndex().add(innerPoint.getIndex());
//                        }
//                    }
//                }
            }

        }
    }

    public static void buildRelation(List<Point> innerPoints, List<Point> outerPoints) {
        for (int i = 0; i < innerPoints.size(); i++) {
            Point innerPoint = innerPoints.get(i);
            for (int j = 0; j < outerPoints.size(); j++) {
                Point outerPoint = outerPoints.get(j);
                if (innerPoint.isDominate(outerPoint)) {
//                    System.out.println("isDominate");
                    innerPoint.getChildrenIndex().add(outerPoint.getIndex());
                    outerPoint.getParentsIndex().add(innerPoint.getIndex());
                }
            }

        }
    }

}

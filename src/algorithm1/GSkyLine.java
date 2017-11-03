package algorithm1;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 2017/11/1.
 */
public class GSkyLine {

    public List<Layer> gSkyLine(List<Point> points) {
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
            if (!layer1.getTailPoint().isDominate(p_u_i)) {
                p_u_i.setLayer(0);
                layer1.setTailPoint(p_u_i);
            } else if (layers.get(layers.size() - 1).getTailPoint().isDominate(p_u_i)) {
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
                        boolean isLeftDominate = layers.get(middle).getTailPoint().isDominate(p_u_i);
                        boolean isRightDominate = layers.get(middle + 1).getTailPoint().isDominate(p_u_i);
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

    public List<Layer> gSkyLineforMultiDimen(List<Point> points) {
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
//                layer1.getLayerPoints().add(p_u_i);
            } else if (layers.get(layers.size() - 1).isDominate(p_u_i)) {
                p_u_i.setLayer(++maxLayerNum);
                Layer newLayer = new Layer();
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
}

package algorithm1;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 2017/11/1.
 */
public class Layer {
    private Point tailPoint;

    private int layerNum;

    private List<Point> layerPoints;

    public Layer() {
        layerPoints = new LinkedList<>();
    }

    public int getLayerNum() {
        return layerNum;
    }

    public void setLayerNum(int layerNum) {
        this.layerNum = layerNum;
    }

    public Point getTailPoint() {
        return tailPoint;
    }

    public void setTailPoint(Point tailPoint) {
        this.tailPoint = tailPoint;
    }

    public List<Point> getLayerPoints() {
        return layerPoints;
    }

    public void setLayerPoints(List<Point> layerPoints) {
        this.layerPoints = layerPoints;
    }

    @Override
    public String toString() {
        return "algorithm1.Layer{" +
                "tailPoint=" + tailPoint +
                ", layerNum=" + layerNum +
                ", layerPoints=" + layerPoints +
                '}';
    }
}

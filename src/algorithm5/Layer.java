package algorithm5;

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
//                "tailPoint=" + tailPoint +
//                ", layerNum=" + layerNum +
                ", layerPoints=" + layerPoints +
                '}';
    }

    @Override
    public int hashCode() {
        int result = tailPoint != null ? tailPoint.hashCode() : 0;
        result = 31 * result + layerNum;
        result = 31 * result + (layerPoints != null ? layerPoints.hashCode() : 0);
        return result;
    }

    public boolean isDominate(Point p2) {
        for (Point point : layerPoints) {
            if (point.isDominate(p2)) {
                return true;
            }
        }
        return false;
    }
}

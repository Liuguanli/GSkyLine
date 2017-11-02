package algorithm_multi_dimension;

import java.util.Arrays;

/**
 * Created by apple on 2017/11/1.
 */
public class Point implements  Comparable{

    private int layer;
    private float[] dimentionalData;

    public Point(String[] args) {
        dimentionalData = new float[args.length];
        for (int i = 0; i < args.length; i++) {
            dimentionalData[i] = Float.valueOf(args[i]);
        }
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public float[] getDimentionalData() {
        return dimentionalData;
    }

    @Override
    public int compareTo(Object o) {
        if (this.dimentionalData[0] > ((Point)o).dimentionalData[0]) {
            return 1;
        } else if (this.dimentionalData[0] < ((Point)o).dimentionalData[0]) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "algorithm1.Point{" +
                "layer=" + layer +
                ", dimentionalData=" + Arrays.toString(dimentionalData) +
                '}';
    }

    public boolean isDominate(Point p2) {
        float[] dims1 = this.getDimentionalData();
        float[] dims2 = p2.getDimentionalData();
        int euqalsNum = 0;
        for (int i = 0; i < dims1.length; i++) {
            if (dims1[i] > dims2[i]) {
                return false;
            } else if (dims1[i] == dims2[i]) {
                euqalsNum++;
            }
        }
        if (euqalsNum == dims1.length) {
            return false;
        }
        return true;
    }
}

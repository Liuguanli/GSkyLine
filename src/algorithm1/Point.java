package algorithm1;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by apple on 2017/11/1.
 */
public class Point implements Comparable {

    private int layer;
    private BigDecimal[] dimentionalData;

    public Point(String[] args) {
        dimentionalData = new BigDecimal[args.length];
        for (int i = 0; i < args.length; i++) {
            dimentionalData[i] = new BigDecimal(args[i]);
        }
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public BigDecimal[] getDimentionalData() {
        return dimentionalData;
    }

    @Override
    public int compareTo(Object o) {
        return this.dimentionalData[0].compareTo(((Point)o).dimentionalData[0]);
    }

    @Override
    public String toString() {
        return "algorithm1.Point{" +
                "layer=" + layer +
                ", dimentionalData=" + Arrays.toString(dimentionalData) +
                '}';
    }

    public boolean isDominate(Point p2) {
        BigDecimal[] dims1 = this.getDimentionalData();
        BigDecimal[] dims2 = p2.getDimentionalData();
        int euqalsNum = 0;
        for (int i = 0; i < dims1.length; i++) {
            if (dims1[i].compareTo(dims2[i]) == 1) {
                return false;
            } else if (dims1[i].compareTo(dims2[i]) == 0) {
                euqalsNum++;
            }
        }
        if (euqalsNum == dims1.length) {
            return false;
        }
        return true;
    }
}

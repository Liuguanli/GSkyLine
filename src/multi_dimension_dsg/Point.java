package multi_dimension_dsg;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 2017/11/1.
 */
public class Point implements Comparable {

    private int layer;
    private float[] dimentionalData;

    private List<Point> parents;
    private List<Point> children;

    private List<Integer> parentsIndex;
    private List<Integer> childrenIndex;

    private int index;

    public List<Integer> getParentsIndex() {
        return parentsIndex;
    }

    public void setParentsIndex(List<Integer> parentsIndex) {
        this.parentsIndex = parentsIndex;
    }

    public List<Integer> getChildrenIndex() {
        return childrenIndex;
    }

    public void setChildrenIndex(List<Integer> childrenIndex) {
        this.childrenIndex = childrenIndex;
    }

    public Point(String[] args, int index) {
        dimentionalData = new float[args.length];
        parents = new LinkedList<>();
        children = new LinkedList<>();
        parentsIndex = new LinkedList<>();
        childrenIndex = new LinkedList<>();
        this.index = index;

        for (int i = 0; i < args.length; i++) {
            dimentionalData[i] = Float.valueOf(args[i]);
        }
    }

    public int getIndex() {
        return index;
    }

    public List<Point> getParents() {
        return parents;
    }

    public void setParents(List<Point> parents) {
        this.parents = parents;
    }

    public List<Point> getChildren() {
        return children;
    }

    public void setChildren(List<Point> children) {
        this.children = children;
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
        if (this.dimentionalData[0] > ((Point) o).dimentionalData[0]) {
            return 1;
        } else if (this.dimentionalData[0] < ((Point) o).dimentionalData[0]) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Point{" +
                "layer=" + layer +
                ", dimentionalData=" + Arrays.toString(dimentionalData) +
                ", parentsIndex=" + parentsIndex +
                ", childrenIndex=" + childrenIndex +
                ", index=" + index +
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

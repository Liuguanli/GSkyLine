package algorithm5;


import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 2017/11/2.
 */
public class Group {

    private int index;
    private List<Point> points;

    public Group() {
        points = new LinkedList<>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (this.points.size() == ((Group) o).getPoints().size()) {
            for (int i = 0; i < points.size(); i++) {
                if (!((Group) o).getPoints().contains(points.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private BigDecimal claEachDimension(Group group, int i) {
        BigDecimal bigDecimal = new BigDecimal(group.getPoints().get(0).getDimentionalData()[i].doubleValue());
        for (int j = 1; j < group.getPoints().size(); j++) {
            bigDecimal = bigDecimal.add(group.getPoints().get(j).getDimentionalData()[i]);
        }
        return bigDecimal;
    }

    public boolean isDominate(Group group) {
        int dimension = group.getPoints().get(0).getDimentionalData().length;
        for (int i = 0; i < dimension; i++) {
            if (claEachDimension(this, i).compareTo(claEachDimension(group, i)) == 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Group{" +
//                "index=" + index +
                ", points=" + points +
                '}';
    }
}

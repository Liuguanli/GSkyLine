package algorithm3;


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

    @Override
    public String toString() {
        return "Group{" +
//                "index=" + index +
                ", points=" + points +
                '}';
    }
}

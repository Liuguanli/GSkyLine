package algorithm3;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by apple on 2017/11/2.
 */
public class UnitGroup {

    private List<Group> units;

    public UnitGroup() {
        units = new LinkedList<>();
    }

    public List<Group> getUnits() {
        return units;
    }

    public void setUnits(List<Group> units) {
        this.units = units;
    }
}

package algorithm5;

import java.util.*;

/**
 * Created by apple on 2017/11/1.
 */
public class GSkyLine3 {

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

    public void dsg(List<Layer> layers) {
        if (layers == null || layers.size() < 2)
            return;
        int index = 0;
        for (int i = 1; i < layers.size(); i++) {
            Layer outer = layers.get(i);
            List<Point> outerPoints = outer.getLayerPoints();
            for (int j = 0; j < i; j++) {
                Layer inner = layers.get(j);
                List<Point> innerPoints = inner.getLayerPoints();
                buildRelation(innerPoints, outerPoints);
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

    public void buildRelation(List<Point> innerPoints, List<Point> outerPoints) {
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

    public List<Group> unitgroupwise(List<Point> points, int k) {
        List<Group> G_Skylines = new LinkedList<>();
        // build 1-unit group as candidate groups following reverse order of point index
        List<Group> one_unit_group = new LinkedList<>();
        for (int i = 0; i < points.size(); i++) {

            Point point = points.get(i);

//            if ((Math.abs(point.getDimentionalData()[0] - 167.9396f)) < 0.00001f && (Math.abs(point.getDimentionalData()[1] - 0.452f)) < 0.00001f) {
//                System.out.print("");
//            }

            Group unitGroup = new Group();
            List<Integer> parentIndex = point.getParentsIndex();
            if (parentIndex.size() >= k) {
                continue;
            }
            for (int j = 0; j < parentIndex.size(); j++) {
                unitGroup.getPoints().add(points.get(parentIndex.get(j)));
            }
            unitGroup.getPoints().add(point);
            if (unitGroup.getPoints().size() == k) {
                G_Skylines.add(unitGroup);
            } else {
                one_unit_group.add(unitGroup);
            }
        }
        System.out.println("G_Skylines：" + G_Skylines.size());
        System.out.println("G_Skylines contents ：" + G_Skylines);
        // reverse order
        Collections.sort(one_unit_group, new Comparator<Group>() {
            @Override
            public int compare(Group o1, Group o2) {
                if (o1.getPoints().size() > o2.getPoints().size()) {
                    return 1;
                } else if (o1.getPoints().size() < o2.getPoints().size()) {
                    return -1;
                }
                return 0;
            }
        });
        // 给 每一个one unit 赋予下标
        for (int i = 0; i < one_unit_group.size(); i++) {
            one_unit_group.get(i).setIndex(i);
        }

        System.out.println("one_unit_group大小：" + one_unit_group.size());

        for (int i = 0; i < one_unit_group.size(); i++) {
            Group candidateGroup = one_unit_group.get(i);
            Group gLast = getGlast(candidateGroup, one_unit_group, i + 1, k);
            if (gLast.getPoints().size() == k) {
                G_Skylines.add(gLast);
                break;
            } else if (gLast.getPoints().size() < k) {
                break;
            }

            int l = 2;
            List<Group> candidateGroups = new LinkedList<Group>() {
                {
                    add(candidateGroup);
                }
            };
            while (candidateGroups.size() > 0) {
                System.out.println("candidateGroups大小：" + candidateGroups.size());
                List<Group> tempCandidateGroup = new LinkedList<>();
//                System.out.println("while begin ------" + l + "-----");
                for (int j = 0; j < candidateGroups.size(); j++) {
                    Set<Point> parentSet = new HashSet<>();
                    Group candidate = candidateGroups.get(j);
                    List<Point> groupPoints = candidate.getPoints();
                    for (int m = 0; m < groupPoints.size(); m++) {
                        List<Integer> parentsIndex = groupPoints.get(m).getParentsIndex();
                        for (int n = 0; n < parentsIndex.size(); n++) {
                            parentSet.add(points.get(parentsIndex.get(n)));
                        }
                    }
                    int tailSetBeginIndex = candidate.getIndex();

                    List<Group> unit_group = new LinkedList<>();
                    List<Point> deletePoints = new LinkedList<>();
                    boolean isRemovable = true;
                    for (int m = tailSetBeginIndex + 1; m < one_unit_group.size(); m++) {
                        Group uj = one_unit_group.get(m);
                        for (int n = 0; n < uj.getPoints().size(); n++) {
                            if (!parentSet.contains(uj.getPoints().get(n))) {
                                List<Point> candidatePoints = new LinkedList<>(candidate.getPoints());
//                                System.out.println("candidatePoints.size()：" + candidatePoints.size());
                                List<Point> ujPoints = uj.getPoints();
                                for (int o = 0; o < ujPoints.size(); o++) {
                                    if (!candidatePoints.contains(ujPoints.get(o))) {
                                        candidatePoints.add(ujPoints.get(o));
                                    }
                                }
//                                System.out.println("candidatePoints.size()：" + candidatePoints.size());
                                if (candidatePoints.size() == k) {
                                    Group group = new Group();
                                    group.setPoints(candidatePoints);
                                    G_Skylines.add(group);
                                } else if (candidatePoints.size() < k) {
                                    Group temp = new Group();
                                    temp.setIndex(uj.getIndex());
                                    temp.setPoints(candidatePoints);
                                    tempCandidateGroup.add(temp);
                                }
                                break;
                            }
                        }

                    }

                }


//                System.out.println("while end ------" + l + "-----");
//                candidateGroup.clear();
                candidateGroups = tempCandidateGroup;
                System.out.println("tempCandidateGroup：" + tempCandidateGroup.size());
                System.out.println("G_Skylines：" + G_Skylines.size());
//                System.out.println("candidateGroup大小:" + candidateGroup.size());
                l++;
            }
        }
        return G_Skylines;
    }

    public Group getGlast(Group group, List<Group> groups, int from, int k) {
        long begin = System.currentTimeMillis();
        Set<Point> result = new HashSet<>();
        result.addAll(group.getPoints());
        for (int i = from; i < groups.size(); i++) {
            result.addAll(groups.get(i).getPoints());
        }
        List<Point> points = new LinkedList<>(result);
        Group gLast = new Group();
        gLast.setPoints(points);
        long end = System.currentTimeMillis();
        return gLast;
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

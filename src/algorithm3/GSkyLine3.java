package algorithm3;

import java.io.IOException;
import java.util.*;

/**
 * Created by apple on 2017/11/1.
 */
public class GSkyLine3 {
    public static void main(String[] args) {
        DataSetReader reader = new DataSetReader();
        try {

            long begin = System.currentTimeMillis();
            List<Point> dataItems = reader.read("datasets/anti_2.txt");
            long end = System.currentTimeMillis();
            System.out.println("读数据耗时:" + (end - begin));
            System.out.println(dataItems.size());
            Collections.sort(dataItems);
//            for (Point point : dataItems) {
//                System.out.println(point);
//            }
            begin = System.currentTimeMillis();
            List<Layer> layers = gSkyLine(dataItems);
            end = System.currentTimeMillis();
            System.out.println("gSkyLine耗时:" + (end - begin));
            begin = System.currentTimeMillis();
            dsg(layers);
            end = System.currentTimeMillis();
            System.out.println("dsg耗时:" + (end - begin));
            int k = 3;
            begin = System.currentTimeMillis();
            List<Group> groups = unitgroupwise(dataItems, k);
            end = System.currentTimeMillis();
            System.out.println("unitgroupwise耗时:" + (end - begin));
            System.out.println("group大小：" + groups.size());

//            int sum = 0;
//            for (Layer layer : layers) {
//                sum += layer.getLayerPoints().size();
//                System.out.println(layer);
//                break;
//            }
//            System.out.println(sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }

    public static List<Layer> gSkyLine(List<Point> points) {
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

    public static void dsg(List<Layer> layers) {
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

    public static void buildRelation(List<Point> innerPoints, List<Point> outerPoints) {
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

    public static List<Group> unitgroupwise(List<Point> points, int k) {

        List<Group> G_Skylines = new LinkedList<>();

        // build 1-unit group as candidate groups following reverse order of point index
        List<Group> one_unit_group = new LinkedList<>();
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            Group unitGroup = new Group();
            List<Integer> parentIndex = point.getParentsIndex();
            if (parentIndex.size() >= k) {
                continue;
            }
            for (int j = 0; j < parentIndex.size(); j++) {
                unitGroup.getPoints().add(points.get(parentIndex.get(j)));
            }
            unitGroup.getPoints().add(point);
            one_unit_group.add(unitGroup);
        }
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
            Group group = one_unit_group.get(i);
            Group gLast = getGlast(group, one_unit_group, i + 1);
            if (gLast.getPoints().size() == k) {
                G_Skylines.add(gLast);
                break;
            } else if (gLast.getPoints().size() < k) {
                break;
            }
            int l = 2;
            List<Group> candidateGroup = new LinkedList<Group>() {
                {

                    for (int j = 0; j < one_unit_group.size(); j++) {
                        if (one_unit_group.get(j).getPoints().size() == 1) {
                            add(one_unit_group.get(j));
                        }
                    }
                }
            };

            while (candidateGroup.size() > 0) {

                List<Group> tempCandidateGroup = new LinkedList<>();
                System.out.println("while begin ------" + l + "-----");
                for (int j = 0; j < candidateGroup.size(); j++) {
                    Set<Point> parentSet = new HashSet<>();
                    Group candidate = candidateGroup.get(j);
                    List<Point> groupPoints = candidate.getPoints();
                    for (int m = 0; m < groupPoints.size(); m++) {
                        List<Integer> parentsIndex = groupPoints.get(m).getParentsIndex();
                        for (int n = 0; n < parentsIndex.size(); n++) {
                            parentSet.add(points.get(parentsIndex.get(n)));
                        }
                    }
                    System.out.println("who is computing? ---" + j + "---");
                    int tailSetBeginIndex = candidate.getIndex();

                    List<Group> unit_group = new LinkedList<>();
                    List<Point> deletePoints = new LinkedList<>();
                    boolean isRemovable = true;
                    for (int m = tailSetBeginIndex + 1; m < one_unit_group.size(); m++) {
                        Group uj = one_unit_group.get(m);
                        for (int n = 0; n < uj.getPoints().size(); n++) {
                            if (!parentSet.contains(uj.getPoints().get(n))) {
                                List<Point> candidatePoints = candidate.getPoints();
                                List<Point> ujPoints = uj.getPoints();
                                for (int o = 0; o < ujPoints.size(); o++) {
                                    if (!candidatePoints.contains(ujPoints.get(o))) {
                                        candidatePoints.add(ujPoints.get(o));
                                    }
                                }
                                if (candidatePoints.size() == k) {
                                    G_Skylines.add(candidate);
                                } else if (candidatePoints.size() < k) {
                                    candidate.setIndex(uj.getIndex());
                                    candidate.setPoints(candidatePoints);
                                    tempCandidateGroup.add(candidate);
                                }
                            }
                        }

                    }

                }
                System.out.println("while end ------" + l + "-----");
//                candidateGroup.clear();
                candidateGroup = tempCandidateGroup;
                System.out.println("candidateGroup大小:" + candidateGroup.size());
                l++;
            }
        }
        return G_Skylines;
    }

    public static Group getGlast(Group group, List<Group> groups, int from) {
        Set<Point> result = new HashSet<>();
        result.addAll(group.getPoints());
        for (int i = from; i < groups.size(); i++) {
            result.addAll(groups.get(i).getPoints());
        }
        List<Point> points = new LinkedList<>(result);
        Group gLast = new Group();
        gLast.setPoints(points);
        return gLast;
    }

}

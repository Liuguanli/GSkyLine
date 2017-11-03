package algorithm2;

import java.util.*;

/**
 * Created by apple on 2017/11/1.
 */
public class GSkyLine2 {

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
        System.out.println("层数：" + layers.size());
        int iter = 0;
        for (int i = 1; i < layers.size(); i++) {
            Layer outer = layers.get(i);
            List<Point> outerPoints = outer.getLayerPoints();
            for (int j = 0; j < i; j++) {
                Layer inner = layers.get(j);
                List<Point> innerPoints = inner.getLayerPoints();
                long begin = System.currentTimeMillis();
                buildRelation(innerPoints, outerPoints);
                long end = System.currentTimeMillis();
//                if (end - begin > 10) {
//                    System.out.println("buildRelation cost:" + (end - begin));
//                    System.out.println("index i=" + i + " j=" + j);
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


    public List<Group> pointWise(List<Point> points, int k) {
        Group group0 = new Group();
        group0.setIndex(0);
        LinkedList<Group>[] G_Skylines = new LinkedList[k + 1];
        G_Skylines[0] = new LinkedList<>();
        G_Skylines[0].add(group0);
        for (int i = 0; i < k; i++) {
            LinkedList<Group> G_Skyline = G_Skylines[i];
            for (int l = 0; l < G_Skyline.size(); l++) {
//                System.out.println("G_Skyline.size()->" + G_Skyline.size());
                Group group = G_Skyline.get(l);
                Set<Point> tailSet = new HashSet<>(points);
                Set<Point> childrenSet = new HashSet<>();
                for (Point point : group.getPoints()) {
                    List<Integer> childrenIndex = point.getChildrenIndex();
                    for (int j = 0; j < childrenIndex.size(); j++) {
                        Point child = points.get(childrenIndex.get(j));
                        childrenSet.add(child);
                    }
                }

                Iterator<Point> iterator = tailSet.iterator();
                Set<Point> removePoints = new HashSet<>();
                int maxLayer = finMaxLayer(group);
                while (iterator.hasNext()) {
                    Point p_j = iterator.next();
                    if (!childrenSet.contains(p_j) && p_j.getLayer() != 0) {
                        removePoints.add(p_j);
                    }
                    if (p_j.getLayer() - maxLayer >= 2) {
                        removePoints.add(p_j);
                    }
                }
//                System.out.println("before remove " + tailSet.size());
                tailSet.removeAll(removePoints);
//                System.out.println("after remove " + tailSet.size());

                iterator = tailSet.iterator();
                while (iterator.hasNext()) {
                    Point point = iterator.next();
                    List<Integer> parents = point.getParentsIndex();
                    boolean isAddable = true;
                    for (int j = 0; j < parents.size(); j++) {
                        if (!group.getPoints().contains(points.get(parents.get(j)))) {
                            isAddable = false;
                            break;
                        }
                    }
                    if (isAddable) {
                        Group newGroup = new Group();
                        newGroup.getPoints().addAll(group.getPoints());
                        newGroup.getPoints().add(point);
//                        System.out.println("newGroup->" + newGroup);
//                        System.out.println("group->" + group);
                        newGroup.setIndex(G_Skyline.size());
                        if (G_Skylines[i + 1] == null) {
                            G_Skylines[i + 1] = new LinkedList<>();
                        }
                        G_Skylines[i + 1].add(newGroup);
//                        if (!G_Skyline.contains(newGroup)) {
//                            G_Skyline.add(newGroup);
//                        }
                    }
                }
            }
        }
//        List<Group> result = new LinkedList<>();
//        for (int i = 0; i < G_Skyline.size(); i++) {
//            if (G_Skyline.get(i).getPoints().size() == k) {
//                result.add(G_Skyline.get(i));
//            }
//        }
        return G_Skylines[k];
    }

    public int finMaxLayer(Group group) {
        int maxLayer = 0;
        List<Point> points = group.getPoints();
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            if (point.getLayer() > maxLayer) {
                maxLayer = point.getLayer();
            }
        }
        return maxLayer;
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

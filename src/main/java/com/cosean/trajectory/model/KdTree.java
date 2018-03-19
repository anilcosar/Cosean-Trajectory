package com.cosean.trajectory.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private static final Logger logger = LoggerFactory.getLogger(KdTree.class);
    private static final QueryField CONTAINER = new QueryField(-1000, -1000, 1000, 1000);

    private static class KdNode {
        private KdNode left;
        private KdNode right;
        private final boolean vertical;
        private final double latitude;
        private final double longitude;

        KdNode(double lat, double lng, KdNode left,
               KdNode right, boolean vert) {
            this.latitude = lat;
            this.longitude = lng;
            this.left = left;
            this.right = right;
            vertical = vert;
        }
    }

    private KdNode root;

    public KdTree() {
        root = null;
    }

    public void printTree() {
        logger.info("Root : x: " + root.latitude + " y: " + root.longitude);
        printTree(root.left, root.right);
    }

    private void printTree(KdNode left, KdNode right) {
        if (left != null) {
            logger.info("Node : x: " + left.latitude + " y: " + left.longitude);
            printTree(left.left, left.right);
        }
        if (right != null) {
            logger.info("Node : x: " + right.latitude + " y: " + right.longitude);
            printTree(right.left, right.right);
        }
    }

    private KdNode insert(KdNode node, Point p, boolean vertical) {
        if (node == null) {
            //yeni olustur
            return new KdNode(p.getLatitude(), p.getLongitude(), null, null, vertical);
        }

        //zaten varsa onu cevir
        if (node.latitude == p.getLatitude() && node.longitude == p.getLongitude()) return node;

        // yerlestirme
        if (node.vertical && p.getLatitude() < node.latitude || !node.vertical && p.getLongitude() < node.longitude)
            node.left = insert(node.left, p, !node.vertical);
        else
            node.right = insert(node.right, p, !node.vertical);

        return node;
    }

    public void insert(Point p) {
        root = insert(root, p, true);
    }


    private void range(KdNode node, QueryField nrect,
                       QueryField rectangle, List<Point> results) {
        if (node == null) return;

        if (rectangle.intersects(nrect)) {
            final Point p = new Point(node.latitude, node.longitude);
            if (rectangle.contains(p)) results.add(p);
            range(node.left, leftRect(nrect, node), rectangle, results);
            range(node.right, rightRect(nrect, node), rectangle, results);
        }
    }

    public List<Point> range(QueryField rect) {
        List<Point> queriedList = new ArrayList<>();
        range(root, CONTAINER, rect, queriedList);
        logger.info("Range : " + queriedList);
        return queriedList;
    }

    private QueryField leftRect(QueryField rect, KdNode node) {
        if (node.vertical)
            return new QueryField(rect.xmin(), rect.ymin(), node.latitude, rect.ymax());
        else
            return new QueryField(rect.xmin(), rect.ymin(), rect.xmax(), node.longitude);
    }

    private QueryField rightRect(QueryField rect, KdNode node) {
        if (node.vertical)
            return new QueryField(node.latitude, rect.ymin(), rect.xmax(), rect.ymax());
        else
            return new QueryField(rect.xmin(), node.longitude, rect.xmax(), rect.ymax());
    }

}

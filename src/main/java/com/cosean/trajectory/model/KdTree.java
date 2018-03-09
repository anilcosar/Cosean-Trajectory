package com.cosean.trajectory.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private static final Logger logger = LoggerFactory.getLogger(KdTree.class);
    private static final QueryField CONTAINER = new QueryField(0, 0, 100, 100);


    private static class KdNode {
        private KdNode left;
        private KdNode right;
        private final boolean vertical;
        private final double x;
        private final double y;

        public KdNode(final double x, final double y, final KdNode l,
                      final KdNode r, final boolean v) {
            this.x = x;
            this.y = y;
            left = l;
            right = r;
            vertical = v;
        }
    }

    private KdNode root;
    private int size;

    // construct an empty tree of points
    public KdTree() {
        size = 0;
        root = null;
    }

    // does the tree contain the point p?
    public boolean contains(final Point p) {
        return contains(root, p.getLatitude(), p.getLongitude());
    }

    private boolean contains(KdNode node, double x, double y) {
        if (node == null) return false;
        if (node.x == x && node.y == y) return true;

        if (node.vertical && x < node.x || !node.vertical && y < node.y)
            return contains(node.left, x, y);
        else
            return contains(node.right, x, y);
    }

    public void printTree() {
        logger.info("Root : x: " + root.x + " y: " + root.y);
        printTree(root.left, root.right);
    }

    private void printTree(KdNode left, KdNode right) {
        if (left != null) {
            logger.info("Node : x: " + left.x + " y: " + left.y);
            printTree(left.left, left.right);
        }
        if (right != null) {
            logger.info("Node : x: " + right.x + " y: " + right.y);
            printTree(right.left, right.right);
        }
    }

    private KdNode insert(final KdNode node, final Point p,
                          final boolean vertical) {
        // if new node, create it
        if (node == null) {
            size++;
            return new KdNode(p.getLatitude(), p.getLongitude(), null, null, vertical);
        }

        // if already in, return it
        if (node.x == p.getLatitude() && node.y == p.getLongitude()) return node;

        // else, insert it where corresponds (left - right recursive call)
        if (node.vertical && p.getLatitude() < node.x || !node.vertical && p.getLongitude() < node.y)
            node.left = insert(node.left, p, !node.vertical);
        else
            node.right = insert(node.right, p, !node.vertical);

        return node;
    }

    public void insert(final Point p) {
        root = insert(root, p, true);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void range(final KdNode node, final QueryField nrect,
                       final QueryField rect, final List<Point> queue)
    {
        if (node == null) return;

        if (rect.intersects(nrect)) {
            final Point p = new Point(node.x, node.y);
            if (rect.contains(p)) queue.add(p);
            range(node.left, leftRect(nrect, node), rect, queue);
            range(node.right, rightRect(nrect, node), rect, queue);
        }
    }

    // all points in the set that are inside the rectangle
    public List<Point> range(final QueryField rect) {
        List<Point> queriedList = new ArrayList<>();
        range(root, CONTAINER,rect, queriedList);
        logger.info("Range : "+queriedList);
        return queriedList;
    }

    private QueryField leftRect(final QueryField rect, final KdNode node)
    {
        if (node.vertical)
            return new QueryField(rect.xmin(), rect.ymin(), node.x, rect.ymax());
        else
            return new QueryField(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
    }

    private QueryField rightRect(final QueryField rect, final KdNode node)
    {
        if (node.vertical)
            return new QueryField(node.x, rect.ymin(), rect.xmax(), rect.ymax());
        else
            return new QueryField(rect.xmin(), node.y, rect.xmax(), rect.ymax());
    }

}

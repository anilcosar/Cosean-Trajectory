package com.cosean.trajectory.model;

public class QueryField {

    private final double xmin, ymin;   // minimum x- and y-coordinates
    private final double xmax, ymax;   // maximum x- and y-coordinates

    public QueryField(double xmin, double ymin, double xmax, double ymax) {

        if (xmin < xmax && ymin < ymax) {
            this.xmin = xmin;
            this.ymin = ymin;
            this.xmax = xmax;
            this.ymax = ymax;
        } else if (xmin < xmax && ymin > ymax) {
            this.xmin = xmin;
            this.ymin = ymax;
            this.xmax = xmax;
            this.ymax = ymin;
        } else if (xmin > xmax && ymin > ymax) {
            this.xmin = xmax;
            this.ymin = ymax;
            this.xmax = xmin;
            this.ymax = ymin;
        } else {
            this.xmin = xmax;
            this.ymin = ymin;
            this.xmax = xmin;
            this.ymax = ymax;

        }

    }

    public double xmin() {
        return xmin;
    }

    public double ymin() {
        return ymin;
    }

    public double xmax() {
        return xmax;
    }

    public double ymax() {
        return ymax;
    }

    public boolean intersects(QueryField that) {
        return this.xmax >= that.xmin && this.ymax >= that.ymin
                && that.xmax >= this.xmin && that.ymax >= this.ymin;
    }

    public boolean contains(Point p) {
        return (p.getLatitude() >= xmin) && (p.getLatitude() <= xmax)
                && (p.getLongitude() >= ymin) && (p.getLongitude() <= ymax);
    }

}

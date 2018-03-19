package com.cosean.trajectory.model;

public class QueryField {

    private final double xMin, yMin;
    private final double xMax, yMax;

    public QueryField(double xMin, double yMin, double xMax, double yMax) {

        if (xMin < xMax && yMin < yMax) {
            this.xMin = xMin;
            this.yMin = yMin;
            this.xMax = xMax;
            this.yMax = yMax;
        } else if (xMin < xMax && yMin > yMax) {
            this.xMin = xMin;
            this.yMin = yMax;
            this.xMax = xMax;
            this.yMax = yMin;
        } else if (xMin > xMax && yMin > yMax) {
            this.xMin = xMax;
            this.yMin = yMax;
            this.xMax = xMin;
            this.yMax = yMin;
        } else {
            this.xMin = xMax;
            this.yMin = yMin;
            this.xMax = xMin;
            this.yMax = yMax;
        }

    }

    public double xmin() {
        return xMin;
    }

    public double ymin() {
        return yMin;
    }

    public double xmax() {
        return xMax;
    }

    public double ymax() {
        return yMax;
    }

    public boolean intersects(QueryField that) {
        return this.xMax >= that.xMin && this.yMax >= that.yMin
                && that.xMax >= this.xMin && that.yMax >= this.yMin;
    }

    public boolean contains(Point p) {
        return (p.getLatitude() >= xMin) && (p.getLatitude() <= xMax)
                && (p.getLongitude() >= yMin) && (p.getLongitude() <= yMax);
    }

}

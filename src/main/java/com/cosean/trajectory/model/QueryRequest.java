package com.cosean.trajectory.model;

import java.util.List;
import java.util.Objects;

public class QueryRequest {
    private List<Point> coordinates;
    private Point start;
    private Point end;

    public QueryRequest(List<Point> coordinates, Point start, Point end) {
        this.coordinates = coordinates;
        this.start = start;
        this.end = end;
    }

    public QueryRequest() {
    }

    public List<Point> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Point> coordinates) {
        this.coordinates = coordinates;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryRequest request = (QueryRequest) o;
        return Objects.equals(coordinates, request.coordinates) &&
                Objects.equals(start, request.start) &&
                Objects.equals(end, request.end);
    }

    @Override
    public int hashCode() {

        return Objects.hash(coordinates, start, end);
    }

    @Override
    public String toString() {
        return "QueryRequest{" +
                "coordinates=" + coordinates +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}

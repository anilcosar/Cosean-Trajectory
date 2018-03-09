package com.cosean.trajectory.service;

import com.cosean.trajectory.model.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReductionService {

    public ResponseEntity simplify(List<Point> coordList) {
        List<Point> pointListOut = new ArrayList<>();
        ramerDouglasPeucker(coordList,0.001,pointListOut);
        return new ResponseEntity<>(pointListOut,HttpStatus.OK);
    }

    private static double perpendicularDistance(Point pt, Point lineStart, Point lineEnd) {
        double dx = lineEnd.getLatitude() - lineStart.getLatitude();
        double dy = lineEnd.getLongitude() - lineStart.getLongitude();

        // Normalize
        double mag = Math.hypot(dx, dy);
        if (mag > 0.0) {
            dx /= mag;
            dy /= mag;
        }
        double pvx = pt.getLatitude() - lineStart.getLatitude();
        double pvy = pt.getLongitude() - lineStart.getLongitude();

        // Get dot product (project pv onto normalized direction)
        double pvdot = dx * pvx + dy * pvy;

        // Scale line direction vector and subtract it from pv
        double ax = pvx - pvdot * dx;
        double ay = pvy - pvdot * dy;

        return Math.hypot(ax, ay);
    }

    private static void ramerDouglasPeucker(List<Point> pointList, double epsilon, List<Point> out) {
        if (pointList.size() < 2) throw new IllegalArgumentException("Not enough points to simplify");

        // Find the point with the maximum distance from line between the start and end
        double dmax = 0.0;
        int index = 0;
        int end = pointList.size() - 1;
        for (int i = 1; i < end; ++i) {
            double d = perpendicularDistance(pointList.get(i), pointList.get(0), pointList.get(end));
            if (d > dmax) {
                index = i;
                dmax = d;
            }
        }

        // If max distance is greater than epsilon, recursively simplify
        if (dmax > epsilon) {
            List<Point> recResults1 = new ArrayList<>();
            List<Point> recResults2 = new ArrayList<>();
            List<Point> firstLine = pointList.subList(0, index + 1);
            List<Point> lastLine = pointList.subList(index, pointList.size());
            ramerDouglasPeucker(firstLine, epsilon, recResults1);
            ramerDouglasPeucker(lastLine, epsilon, recResults2);

            // build the result list
            out.addAll(recResults1.subList(0, recResults1.size() - 1));
            out.addAll(recResults2);
            if (out.size() < 2) throw new RuntimeException("Problem assembling output");
        } else {
            // Just return start and end points
            out.clear();
            out.add(pointList.get(0));
            out.add(pointList.get(pointList.size() - 1));
        }
    }
}

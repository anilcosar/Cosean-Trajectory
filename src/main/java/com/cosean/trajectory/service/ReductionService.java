package com.cosean.trajectory.service;

import com.cosean.trajectory.model.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReductionService {
    private static final double epsilon = 0.001;

    public ResponseEntity simplify(List<Point> coordList) {
        long startTime = System.nanoTime();
        List<Point> pointListOut = new ArrayList<>();
        ramerDouglasPeucker(coordList, epsilon, pointListOut);
        Map<String, Object> map = new HashMap<>();
        map.put("reducedData", pointListOut);
        map.put("reductionRatio", (1 - ((double) pointListOut.size() / coordList.size())) * 100);
        map.put("reductionDuration", (System.nanoTime() - startTime) / 1000000.0 + " ms");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private static double perpendicularDistance(Point pt, Point lineStart, Point lineEnd) {
        double dx = lineEnd.getLatitude() - lineStart.getLatitude();
        double dy = lineEnd.getLongitude() - lineStart.getLongitude();

        double mag = Math.hypot(dx, dy);
        if (mag > 0.0) {
            dx /= mag;
            dy /= mag;
        }
        double pvx = pt.getLatitude() - lineStart.getLatitude();
        double pvy = pt.getLongitude() - lineStart.getLongitude();

        double pvdot = dx * pvx + dy * pvy;

        double ax = pvx - pvdot * dx;
        double ay = pvy - pvdot * dy;

        return Math.hypot(ax, ay);
    }

    private static void ramerDouglasPeucker(List<Point> pointList, double epsilon, List<Point> out) {
        if (pointList.size() < 2) throw new IllegalArgumentException("Not enough points to simplify");

        double dMax = 0.0;
        int index = 0;
        int end = pointList.size() - 1;
        for (int i = 1; i < end; ++i) {
            double d = perpendicularDistance(pointList.get(i), pointList.get(0), pointList.get(end));
            if (d > dMax) {
                index = i;
                dMax = d;
            }
        }

        if (dMax > epsilon) {
            List<Point> recResults1 = new ArrayList<>();
            List<Point> recResults2 = new ArrayList<>();
            List<Point> firstLine = pointList.subList(0, index + 1);
            List<Point> lastLine = pointList.subList(index, pointList.size());
            ramerDouglasPeucker(firstLine, epsilon, recResults1);
            ramerDouglasPeucker(lastLine, epsilon, recResults2);

            out.addAll(recResults1.subList(0, recResults1.size() - 1));
            out.addAll(recResults2);
            if (out.size() < 2) throw new RuntimeException("Not enough points to simplify");
        } else {
            out.clear();
            out.add(pointList.get(0));
            out.add(pointList.get(pointList.size() - 1));
        }
    }
}

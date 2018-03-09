package com.cosean.trajectory.service;

import com.cosean.trajectory.model.KdTree;
import com.cosean.trajectory.model.Point;
import com.cosean.trajectory.model.QueryField;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    public ResponseEntity query(List<Point> coordList, Point start, Point end) {
        KdTree kdTree =new KdTree();
        coordList.forEach(kdTree::insert);
        List<Point>result=kdTree.range(new QueryField(start.getLatitude(),start.getLongitude(),end.getLatitude(),end.getLongitude()));
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}

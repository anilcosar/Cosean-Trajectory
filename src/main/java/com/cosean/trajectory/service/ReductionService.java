package com.cosean.trajectory.service;

import com.cosean.trajectory.model.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReductionService {

    public ResponseEntity simplify(List<Point> coordList) {
        return new ResponseEntity<>(coordList,HttpStatus.OK);
    }
}

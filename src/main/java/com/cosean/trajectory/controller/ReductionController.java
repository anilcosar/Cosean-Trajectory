package com.cosean.trajectory.controller;

import com.cosean.trajectory.model.Point;
import com.cosean.trajectory.service.ReductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ReductionController {

    private final ReductionService reductionService;

    @Autowired
    public ReductionController(ReductionService reductionService) {
        this.reductionService = reductionService;
    }

    @PostMapping("/reduction")
    public ResponseEntity reduction(@RequestBody List<Point> coordinates ) {
        return reductionService.simplify(coordinates);
    }
}

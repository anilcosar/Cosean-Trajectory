package com.cosean.trajectory.controller;

import com.cosean.trajectory.model.Point;
import com.cosean.trajectory.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class QueryController {

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping("/query")
    public ResponseEntity reduction(@RequestBody List<Point> coordinates ,Point start ,Point end ) {
        return queryService.query(coordinates,start,end);
    }
}

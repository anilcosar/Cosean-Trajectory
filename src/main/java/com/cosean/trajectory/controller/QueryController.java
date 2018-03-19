package com.cosean.trajectory.controller;

import com.cosean.trajectory.model.QueryRequest;
import com.cosean.trajectory.service.QueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description="Query APIs", tags = "Query")
@RequestMapping("/v1")
public class QueryController {

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @ApiOperation(value = "Returns queried points", notes = "Returns a list of queried points,query time and percent of query.", response = ResponseEntity.class)
    @PostMapping("/query")
    public ResponseEntity reduction(@RequestBody QueryRequest queryRequest) {
        return queryService.query(queryRequest.getCoordinates(),queryRequest.getStart(),queryRequest.getEnd());
    }
}

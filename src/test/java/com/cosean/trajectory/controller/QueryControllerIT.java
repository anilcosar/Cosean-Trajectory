package com.cosean.trajectory.controller;

import com.cosean.trajectory.config.BaseIT;
import com.cosean.trajectory.model.Point;
import com.cosean.trajectory.model.QueryRequest;
import com.cosean.trajectory.service.QueryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryControllerIT extends BaseIT {

    @Autowired
    QueryService queryService;

    @Autowired
    TestRestTemplate testRestTemplate;

    List<Point> locationList;

    @Before
    public void init(){
        locationList = new ArrayList<Point>();
        locationList.add(new Point(40.823938, 29.925402));
        locationList.add(new Point(41.824635, 29.923103));
        locationList.add(new Point(42.824708, 29.921665));
        locationList.add(new Point(43.823807, 29.921880));
        locationList.add(new Point(44.823036, 29.922728));
    }
    @Test
    public void should_query_points() throws Exception {
        QueryRequest request= new QueryRequest(locationList,new Point(41,29), new Point(44,30));

        ResponseEntity<List> response = testRestTemplate
                .postForEntity("/v1/query",request,List.class);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(3);
    }

}

package com.cosean.trajectory.controller;

import com.cosean.trajectory.config.BaseIT;
import com.cosean.trajectory.model.Point;
import com.cosean.trajectory.service.ReductionService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ReductionControllerIT extends BaseIT {

    @Autowired
    ReductionService reductionService;

    @Autowired
    TestRestTemplate testRestTemplate;

    List<Point> locationList;

    Logger logger = LoggerFactory.getLogger(ReductionControllerIT.class);
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
    public void should_reduction_points() throws Exception {


        ResponseEntity<Map> response = testRestTemplate
                .postForEntity("/v1/reduction",locationList,Map.class);

        Map map = response.getBody();
        List responseData = (List) map.get("reducedData");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response).isNotNull();
        assertThat(responseData.size()).isEqualTo(3);
        assertThat(map.get("reductionRatio")).isEqualTo(40.0);
    }

}

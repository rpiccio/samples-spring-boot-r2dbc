package com.telefonica.cdo.samples.spring.boot.r2dbc.postgres;

import org.junit.Test;

import com.telefonica.cdo.samples.spring.boot.r2dbc.SeriesControllerTestUtils;

public class SeriesControllerTest extends TestSupport {

    @Test
    public void test() throws Exception {
        SeriesControllerTestUtils.test(webClient);
    }

}

package com.telefonica.cdo.samples.spring.boot.r2dbc.postgres;

import org.junit.Test;

import com.telefonica.cdo.samples.spring.boot.r2dbc.IssueControllerTestUtils;

public class IssueControllerTest extends TestSupport {

    @Test
    public void test() throws Exception {
        IssueControllerTestUtils.test(webClient);
    }

}

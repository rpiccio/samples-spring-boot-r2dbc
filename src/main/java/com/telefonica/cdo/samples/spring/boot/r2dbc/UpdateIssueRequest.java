package com.telefonica.cdo.samples.spring.boot.r2dbc;

import lombok.Getter;
import lombok.Setter;

public class UpdateIssueRequest {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String barcode;

}

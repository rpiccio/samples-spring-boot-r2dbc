package com.telefonica.cdo.samples.spring.boot.r2dbc;

import lombok.Getter;
import lombok.Setter;

public class UpdateBookRequest {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String isbn;

}

package com.telefonica.cdo.samples.spring.boot.r2dbc;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("issue")
public class Issue {

    @Column("id")
    @Getter
    @Id
    @Setter
    private Long id;

    @Column("number")
    @Getter
    @Setter
    private Integer number;

    @Column("qualifier")
    @Getter
    @Setter
    private String qualifier;

    @Column("release_date")
    @Getter
    @Setter
    private Date releaseDate;

    @Column("barcode")
    @Getter
    @Setter
    private String barcode;

    @Column("previews_code")
    @Getter
    @Setter
    private String previewsCode;

}

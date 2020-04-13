package com.telefonica.cdo.samples.spring.boot.r2dbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("series")
public class Series {

    @Column("id")
    @Getter
    @Id
    @Setter
    private Long id;

    @Column("name")
    @Getter
    @Setter
    private String name;

    @Column("volume")
    @Getter
    @Setter
    private String volume;

    @Column("begin_year")
    @Getter
    @Setter
    private Integer beginYear;

    @Column("end_year")
    @Getter
    @Setter
    private Integer endYear;

    @Column("publisher_id")
    @Getter
    @Setter
    private Long publisherId;

    @Column("imprint_id")
    @Getter
    @Setter
    private Long imprintId;

}

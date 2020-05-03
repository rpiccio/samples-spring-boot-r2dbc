package com.telefonica.cdo.samples.spring.boot.r2dbc;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;

@Table("variant")
public class Variant {

    @Column("id")
    @Getter
    @Id
    @Setter
    private Long id;

    @Column("name")
    @Getter
    @Setter
    private String name;

}

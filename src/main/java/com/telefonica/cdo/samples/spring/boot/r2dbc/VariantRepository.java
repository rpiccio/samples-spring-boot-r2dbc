package com.telefonica.cdo.samples.spring.boot.r2dbc;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface VariantRepository extends R2dbcRepository<Variant, Long> {

}

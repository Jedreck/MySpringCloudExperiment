package com.jedreck.tester2.entitys;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "person")
public class PersonEntity {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String name;

    private Integer height;
}

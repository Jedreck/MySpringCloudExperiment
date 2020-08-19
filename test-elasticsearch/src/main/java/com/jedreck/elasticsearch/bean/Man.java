package com.jedreck.elasticsearch.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Man {
    private Integer id;
    private String name;
    private String des;
    private List<String> tag;
}

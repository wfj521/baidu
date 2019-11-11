package com.blog.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Type implements Serializable {
    private Integer id;
    private String typeName;
}

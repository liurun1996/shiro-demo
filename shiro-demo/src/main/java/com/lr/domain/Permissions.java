package com.lr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Permissions {
    private String id;
    private String permissionsName;
    //省略set、get方法等.....
}
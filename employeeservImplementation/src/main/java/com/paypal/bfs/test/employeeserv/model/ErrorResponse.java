package com.paypal.bfs.test.employeeserv.model;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    private String message;
    private List<String> errorList;
}

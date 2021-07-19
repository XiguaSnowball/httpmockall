package com.manage.model;

import lombok.Data;

import java.util.Date;
@Data
public class HttpMock  {
    private static final long serialVersionUID = 1L;

    private  Long id;
    private Date createTime;
    private String serviceName;
    private String methodType;
    private String requestPath;
    private String requestQuery;
    private String requestBody;
    private String responseBody;
    private String description;


}

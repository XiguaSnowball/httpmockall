package com.manage.model;

import lombok.Data;

import java.util.Date;
@Data
public class HttpMockRequestBean extends Page<HttpMock> {
    private static final long serialVersionUID = 1L;

    private  Long id;
//    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;

    private String serviceName;
    private String methodType;
    private String requestPath;
    private String requestQuery;
    private String requestBody;
    private String responseBody;

}

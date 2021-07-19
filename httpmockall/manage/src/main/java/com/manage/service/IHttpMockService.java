package com.manage.service;


import com.manage.model.HttpMock;
import com.manage.model.HttpMockRequestBean;

import java.util.List;

public interface IHttpMockService {
     /**
      * 查询全部
      */
     public List<HttpMock> selectHttpMocks(HttpMockRequestBean httpMock);

     /**
      * 插入
      */
     public int insertHttpMock(HttpMock httpmock);

     /**
      * 更新
      */
     public int updateHttpMock(HttpMock httpmock);

     /**
      * 删除
      */
     public void deleteHttpMock(int id);

}

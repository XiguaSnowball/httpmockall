package com.manage.service.impl;


import com.manage.controller.ManagerController;
import com.manage.mappers.HttpMockMapper;
import com.manage.model.HttpMock;
import com.manage.model.HttpMockRequestBean;
import com.manage.service.IHttpMockService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class HttpMockServiceImpl implements IHttpMockService {
    private static final Logger logger = Logger.getLogger(ManagerController.class);

    @Autowired
    HttpMockMapper httpmockdao;


//    public List<HttpMock> queryAllHttpmockList(){
//        List<HttpMock> list = new ArrayList<HttpMock>();
//        list = httpmockdao.queryAllHttpmockList();
//        return list;
//
//    };

    @Override
    public List<HttpMock> selectHttpMocks(HttpMockRequestBean mock) {
        List<HttpMock> list = httpmockdao.selectByPage(mock);
        return list;
    }

    /**
     * 插入
     */
    @Override
    public int insertHttpMock(HttpMock httpmock) {
        int i = httpmockdao.insertHttpMock(httpmock);
        return i;
    }

    ;

    /**
     * 更新
     */
    @Override
    public int updateHttpMock(HttpMock httpmock) {
        int i = httpmockdao.updateHttpMock(httpmock);
        return i;
    }

    ;

    /**
     * 删除
     */
    @Override
    public void deleteHttpMock(int id) {
        httpmockdao.deleteHttpMock(id);
    }

}

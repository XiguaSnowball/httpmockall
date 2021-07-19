package luna.service.service.impl;


import luna.service.controller.HttpMockController;
import luna.service.mappers.HttpMockMapper;
import luna.service.model.HttpMock;
import luna.service.model.HttpMockRequestBean;
import luna.service.service.IHttpMockService;
import org.apache.log4j.Logger;
//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class HttpMockServiceImpl implements IHttpMockService {
    private static final Logger logger = Logger.getLogger(HttpMockController.class);

    @Autowired(required = true)
    HttpMockMapper httpmockdao;


//    public List<HttpMock> queryAllHttpmockList(){
//        List<HttpMock> list = new ArrayList<HttpMock>();
//        list = httpmockdao.queryAllHttpmockList();
//        return list;
//
//    };

    public List<HttpMock> selectHttpMocks(HttpMock mock) {
        List<HttpMock> list = new ArrayList<HttpMock>();
        list = httpmockdao.selectHttpMocks(mock);
        return list;
    }

    ;

    public HttpMockRequestBean query(HttpMockRequestBean httpMockRequestBean) {
        try {
            int count = httpmockdao.selectCount(httpMockRequestBean);
            logger.info("总count数为：" + count);
            if (count > 0) {
                httpMockRequestBean.buildPage(count);
                List<HttpMock> list = new ArrayList<HttpMock>();
                list = httpmockdao.selectByPage(httpMockRequestBean);
                logger.info("本次查询返回的list size为：" + list.size());
                httpMockRequestBean.setRecords(list);
            }
        } catch (Exception ex) {
            //throw ConsoleException.consoleException(ex);
        }
        return httpMockRequestBean;
    }

    /**
     * 插入
     */
    public int insertHttpMock(HttpMock httpmock) {
        int i = httpmockdao.insertHttpMock(httpmock);
        return i;
    }

    ;

    /**
     * 更新
     */
    public int updateHttpMock(HttpMock httpmock) {
        int i = httpmockdao.updateHttpMock(httpmock);
        return i;
    }

    ;

    /**
     * 删除
     */
    public void deleteHttpMock(int id) {
        httpmockdao.deleteHttpMock(id);

    }

    ;
}

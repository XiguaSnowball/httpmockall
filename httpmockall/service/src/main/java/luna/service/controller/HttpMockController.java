package luna.service.controller;


import luna.service.model.HttpMock;
import luna.service.service.IHttpMockService;
import org.apache.log4j.Logger;
//import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class HttpMockController {
    private static final Logger logger = Logger.getLogger(HttpMockController.class);

    @Autowired
    IHttpMockService httpmockService;

    @ResponseBody
    @RequestMapping(value = "**", method = {RequestMethod.GET})
    public String mockGet(HttpServletRequest request) {

        String path = request.getRequestURI();
        String methodType = request.getMethod();
        //判断query是否为null，如果为null则返回空
        String query = (request.getQueryString() == null) ? "" : request.getQueryString();

        logger.info("访问path为2：" + path);
        logger.info("访问方法类型为3：" + methodType);
        logger.info("访问query为3：" + query);

        List<HttpMock> list = new ArrayList<HttpMock>();
        HttpMock mockRequest = new HttpMock();
        mockRequest.setRequestPath(path);
        //mockRequest.setRequest_query(query);
        list = httpmockService.selectHttpMocks(mockRequest);

        HttpMock mockResponse = new HttpMock();

        if (list.size() > 0) {
            mockResponse = list.get(0);
            String result = mockResponse.getResponseBody();
            logger.info("返回response body为：" + result);
            return result;
        } else{
            return "该mock不存在";
        }
    }

    //**为通配，可以处理所有请求
    @ResponseBody
    @RequestMapping(value = "**", method = {RequestMethod.POST})
    public String mockPost(HttpServletRequest request, @RequestBody String data) {

        String path = request.getRequestURI();
        String methodType = request.getMethod();
        //判断query是否为null，如果为null则返回空
        String query = (request.getQueryString() == null) ? "" : request.getQueryString();

        logger.info("访问path为：" + path);
        logger.info("访问方法类型为：" + methodType);
        logger.info("访问query为：" + query);
        logger.info("请求data数据：" + data);

        List<HttpMock> list = new ArrayList<HttpMock>();
        HttpMock mockRequest = new HttpMock();
        mockRequest.setRequestPath(path);
        //mockRequest.setRequest_body(data);
        list = httpmockService.selectHttpMocks(mockRequest);

        HttpMock mockResponse = new HttpMock();

        if (list.size() > 0) {
            mockResponse = list.get(0);
            String result = mockResponse.getResponseBody();
            logger.info("返回response body为：" + result);
            return result;
        } else{
            return "该mock不存在";

        }
    }
}

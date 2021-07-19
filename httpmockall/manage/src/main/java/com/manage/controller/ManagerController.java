package com.manage.controller;

import com.manage.mappers.HttpMockMapper;
import com.manage.model.HttpMock;
import com.manage.model.HttpMockRequestBean;
import com.manage.model.Result;
import com.manage.service.IHttpMockService;
import com.manage.service.TestAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import org.apache.log4j.Logger;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/httpmock")
public class ManagerController {
    Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    IHttpMockService httpmockService;

    @Autowired
    TestAsyncService service;

    @Autowired
    HttpMockMapper httpmockdao;

    @ResponseBody
    @PostMapping(value = "/queryAll")
    //http://localhost:18099/queryAll?currentPageNum=1&pageSize=20
    public Result mockGet(@RequestBody HttpMockRequestBean httpMockRequestBean) {
        return Result.wrapSuccess(httpmockService.selectHttpMocks(httpMockRequestBean));
    }

    /**
     * 添加
     */
    @ResponseBody
    @PostMapping(value = "/add")
    public Result add(@RequestBody HttpMock httpMockRequest) {
        logger.info("add接口请求数据为：" + httpMockRequest);
        HttpMockRequestBean httpMockRequestBean = new HttpMockRequestBean();
        httpMockRequestBean.setMethodType(httpMockRequest.getMethodType());
        httpMockRequestBean.setRequestPath(httpMockRequest.getRequestPath());
        try {
            if (httpmockdao.selectCount1(httpMockRequestBean) > 0) {
                return Result.wrapError(2, "mock重复。。。");
            } else {
                System.out.println("add");
                return Result.wrapSuccess(httpmockService.insertHttpMock(httpMockRequest));
            }
        } catch (Exception e) {
            return Result.wrapError(-1, "系统错误");
        }

    }

    /**
     * 修改httpmock信息
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public Result update(@RequestBody HttpMock httpMockRequest) {
        logger.info("update请求数据为：" + httpMockRequest);
        HttpMock httpmock = new HttpMock();
        try {
            return Result.wrapSuccess(httpmockService.updateHttpMock(httpmock));
        } catch (Exception e) {
            return Result.wrapError(-1, "系统错误");
        }
    }

    /**
     * 删除httpmock信息
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public Result delete(@RequestParam int id) {
        logger.info("待删除待mock id为：" + id);
        httpmockService.deleteHttpMock(id);
        return Result.wrapSuccess(id);
    }

    @GetMapping("/test1")
    public void test1() {
        System.out.println("获取主线程名称：" + Thread.currentThread().getName());
        service.serviceTest();
        System.out.println("执行成功，返回结果");
    }

    @GetMapping("/test")
    public void test() {
        System.out.println("获取主线程名称：" + Thread.currentThread().getName());

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 50000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        // 执行业务逻辑方法serviceTest()
        executor.execute(new Runnable() {
            @Override
            public void run() {
                service.serviceTest();
            }
        });
        System.out.println("执行完成，向用户响应成功信息");
    }


}




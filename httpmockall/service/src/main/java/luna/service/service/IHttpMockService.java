package luna.service.service;

import luna.service.model.HttpMock;
import luna.service.model.HttpMockRequestBean;

import java.util.List;

public interface IHttpMockService {
    /**
     * 查询全部
     */
    public List<HttpMock> selectHttpMocks(HttpMock httpMock);

    /**
     * 分页查询
     */
    public HttpMockRequestBean query(HttpMockRequestBean httpMockRequestBean);

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

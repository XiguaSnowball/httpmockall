package luna.service.mappers;

import luna.service.model.HttpMock;
import luna.service.model.HttpMockRequestBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HttpMockMapper {

    /**
     * 查询所有httpmock，不分页，一般不用
     */
    public List<HttpMock> selectHttpMocks(HttpMock httpMock);
    public List<HttpMock> selectByPrimaryKey(HttpMock httpMock);

    /**
     * 分页查询httpmock
     */
    public List<HttpMock> selectByPage(HttpMockRequestBean httpMockRequestBean);
    public int selectCount1(HttpMockRequestBean httpMockRequestBean);

    /**
     * 统计httpmock 总数
     */
    public int selectCount(HttpMockRequestBean httpMockRequestBean);

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

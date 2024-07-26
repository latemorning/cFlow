package egovframework.com.dty.hnr.yrc.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.dty.hnr.yrc.service.YrycManageVO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("yrycManageDAO")
public class YrycManageDAO extends EgovComAbstractDAO {

    /**
     * 개인 연차 조회
     *
     * @param searchVO
     * @return
     * @throws Exception
     */
    public List<YrycManageVO> selectYrycManageList(YrycManageVO searchVO) throws Exception {

        return selectList("yrycManageDAO.selectYrycManageList", searchVO);
    }

    public YrycManageVO selectYrycManage(YrycManageVO yrycManageVO) {

        return selectOne("yrycManageDAO.selectYrycManage", yrycManageVO);
    }
}

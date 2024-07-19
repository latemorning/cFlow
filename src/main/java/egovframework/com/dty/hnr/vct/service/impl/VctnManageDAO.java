package egovframework.com.dty.hnr.vct.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.dty.hnr.vct.service.VctnManageVO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("vctnManageDAO")
public class VctnManageDAO extends EgovComAbstractDAO {


  public List<VctnManageVO> selectVctnManageList(VctnManageVO searchVO) throws Exception {

    return selectList("vctnManageDAO.selectVctnManageList", searchVO);
  }

  public int selectVctnManageListTotCnt(VctnManageVO searchVO) throws Exception {

    return selectOne("vctnManageDAO.selectVctnManageListTotCnt", searchVO);
  }
}

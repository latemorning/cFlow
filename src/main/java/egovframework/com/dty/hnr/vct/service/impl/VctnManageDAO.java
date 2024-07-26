package egovframework.com.dty.hnr.vct.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.dty.hnr.vct.service.VctnManage;
import egovframework.com.dty.hnr.vct.service.VctnManageVO;
import egovframework.com.uss.ion.vct.service.IndvdlYrycManage;
import egovframework.com.uss.ion.vct.service.VcatnManage;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;
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

  public int selectVctnManageDplctAt(VctnManageVO vctnManageVO) throws Exception {

    return selectOne("vctnManageDAO.selectVctnManageDplctAt", vctnManageVO);
  }

  public void insertVctnManage(VctnManage vctnManage) throws Exception {

    insert("vctnManageDAO.insertVctnManage", vctnManage);
  }

  public void updtIndvdlYrycManage(IndvdlYrycManage indvdlYrycManage) throws Exception {

    update("vctnManageDAO.updateIndvdlYrycManage", indvdlYrycManage);
  }

  public VctnManageVO selectVctnManage(VctnManageVO vctnManageVO) throws Exception {

    return selectOne("vctnManageDAO.selectVctnManage", vctnManageVO);
  }


}

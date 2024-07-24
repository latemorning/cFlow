package egovframework.com.dty.hnr.vct.service.impl;

import egovframework.com.dty.hnr.vct.service.VctnManageService;
import egovframework.com.dty.hnr.vct.service.VctnManageVO;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import java.util.List;
import javax.annotation.Resource;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service("vctNManageService")
public class VctnManageServiceImpl extends EgovAbstractServiceImpl implements VctnManageService {

  @Resource(name = "vctnManageDAO")
  private VctnManageDAO vctnManageDAO;


  @Override
  public List<VctnManageVO> selectVctnManageList(VctnManageVO searchVO) throws Exception {

    List<VctnManageVO> result = vctnManageDAO.selectVctnManageList(searchVO);

//    int num = result.size();

//    for (int i = 0; i < num; i++) {
//      VcatnManageVO vcatnManageVO1 = result.get(i);
//      vcatnManageVO1.setBgnde(EgovDateUtil.formatDate(vcatnManageVO1.getBgnde(), "-"));
//      vcatnManageVO1.setEndde(EgovDateUtil.formatDate(vcatnManageVO1.getEndde(), "-"));
//      result.set(i, vcatnManageVO1);
//    }

    return result;
  }

  @Override
  public int selectVctnManageListTotCnt(VctnManageVO searchVO) throws Exception {

    return vctnManageDAO.selectVctnManageListTotCnt(searchVO);
  }

}

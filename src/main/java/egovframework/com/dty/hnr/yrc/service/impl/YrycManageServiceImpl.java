package egovframework.com.dty.hnr.yrc.service.impl;

import egovframework.com.dty.hnr.yrc.service.YrycManageService;
import egovframework.com.dty.hnr.yrc.service.YrycManageVO;
import java.util.List;
import javax.annotation.Resource;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

@Service("yrycManageService")
public class YrycManageServiceImpl extends EgovAbstractServiceImpl implements YrycManageService {

    @Resource(name = "yrycManageDAO")
    private YrycManageDAO yrycManageDAO;

    /**
     * 개인 연차 조회
     *
     * @param searchVO
     * @return
     * @throws Exception
     */
    @Override
    public List<YrycManageVO> selectYrycManageList(YrycManageVO searchVO) throws Exception {

        return yrycManageDAO.selectYrycManageList(searchVO);
    }

//  @Override
//  public List<VctnManageVO> selectVctnManageList(VctnManageVO searchVO) throws Exception {

//    List<VctnManageVO> result = vctnManageDAO.selectVctnManageList(searchVO);

//    int num = result.size();

//    for (int i = 0; i < num; i++) {
//      VcatnManageVO vcatnManageVO1 = result.get(i);
//      vcatnManageVO1.setBgnde(EgovDateUtil.formatDate(vcatnManageVO1.getBgnde(), "-"));
//      vcatnManageVO1.setEndde(EgovDateUtil.formatDate(vcatnManageVO1.getEndde(), "-"));
//      result.set(i, vcatnManageVO1);
//    }

//    return result;
//  }

//  @Override
//  public int selectVctnManageListTotCnt(VctnManageVO searchVO) throws Exception {

//    return vctnManageDAO.selectVctnManageListTotCnt(searchVO);
//  }

}

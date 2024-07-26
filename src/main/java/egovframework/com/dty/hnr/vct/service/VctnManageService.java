package egovframework.com.dty.hnr.vct.service;

import egovframework.com.uss.ion.vct.service.VcatnManageVO;
import java.util.List;

public interface VctnManageService {

    List<VctnManageVO> selectVctnManageList(VctnManageVO searchVO) throws Exception;

    int selectVctnManageListTotCnt(VctnManageVO searchVO) throws Exception;

    int selectVctnManageDplctAt(VctnManageVO vctnManageVO) throws Exception;

    String insertVctnManage(VctnManage vctnManage, VctnManageVO vctnManageVO) throws Exception;

    VctnManageVO selectVctnManage(VctnManageVO vctnManageVO) throws Exception;
}

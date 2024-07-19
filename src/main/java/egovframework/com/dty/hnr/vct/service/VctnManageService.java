package egovframework.com.dty.hnr.vct.service;

import java.util.List;

public interface VctnManageService {

  List<VctnManageVO> selectVctnManageList(VctnManageVO searchVO) throws Exception;

  int selectVctnManageListTotCnt(VctnManageVO searchVO) throws Exception;
}

package egovframework.com.dty.hnr.yrc.service;

import java.util.List;

public interface YrycManageService {

    List<YrycManageVO> selectYrycManageList(YrycManageVO searchVO) throws Exception;

    YrycManageVO selectYrycManage(String userId) throws Exception;

    List<YrycManageVO> selectYrycMapList(YrycManageVO searchVO) throws Exception;

    void insertYrycManage(YrycManageVO yrycManageVO) throws Exception;

    void updtYrycManage(YrycManageVO yrycManageVO) throws Exception;

    void deleteYrycManage(YrycManageVO yrycManageVO) throws Exception;
}

package egovframework.com.dty.hnr.yrc.service.impl;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.com.dty.hnr.yrc.service.YrycManageVO;
import egovframework.com.uss.ion.yrc.service.IndvdlYrycManage;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("yrycManageDAO")
public class YrycManageDAO extends EgovComAbstractDAO {

    /**
     * 기본 개인 연차 목록 조회
     * vctn 에서 사용자 남은 휴가 정보 조회
     *
     * @param searchVO
     * @return
     * @throws Exception
     */
    public List<YrycManageVO> selectYrycManageList(YrycManageVO searchVO) throws Exception {

        return selectList("yrycManageDAO.selectYrycManageList", searchVO);
    }

    /**
     * 휴가 정보 등록 시 사용하는 목록
     *
     * @param searchVO
     * @return
     * @throws Exception
     */
    public List<YrycManageVO> selectYrycMapList(YrycManageVO searchVO) throws Exception {

        return selectList("yrycManageDAO.selectYrycMapList", searchVO);
    }


    public void insertYrycManage(YrycManageVO yrycManageVO) throws Exception {

        insert("yrycManageDAO.insertYrycManage", yrycManageVO);
    }


    public void updtYrycManage(YrycManageVO yrycManageVO) throws Exception {

        update("yrycManageDAO.updtYrycManage", yrycManageVO);
    }


    public void deleteYrycManage(YrycManageVO yrycManageVO) throws Exception {

        delete("yrycManageDAO.deleteYrycManage", yrycManageVO);
    }

}

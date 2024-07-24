package egovframework.com.dty.hnr.vct.web;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.dty.hnr.vct.service.VctnManage;
import egovframework.com.cmm.util.ApiResponse;
import egovframework.com.cmm.util.ApiResponseCode;
import egovframework.com.dty.hnr.vct.service.VctnManageService;
import egovframework.com.dty.hnr.vct.service.VctnManageVO;
import egovframework.com.dty.hnr.yrc.service.YrycManageService;
import egovframework.com.dty.hnr.yrc.service.YrycManageVO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.egovframe.rte.fdl.string.EgovDateUtil;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * VctnManageController
 *
 * @author KTH
 * @since 24. 7. 15.
 **/
@Controller
public class VctnManageController {

    @Autowired
    VctnManageService vctnManageService;

    @Autowired
    YrycManageService yrycManageService;

    /**
     * 조회 샘플
     *
     * @param itemSeq
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/dty/hnr/vct/vctnManageTest/{itemSeq}")
    public ApiResponse<Object> vctnManageItem(@PathVariable Integer itemSeq) throws Exception {

        VctnManage vctnManage = new VctnManage();

        vctnManage.setBgnde("20240722");
        vctnManage.setEndde("20240722");

        return ApiResponse.success(new Object[]{vctnManage}, null, ApiResponseCode.READ_SUCCESS.getMessage());
    }


    /**
     * 휴가 관리 목록 화면 이동
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/dty/hnr/vct/VctnManageList.do")
    public String vctnManageList(ModelMap model, HttpServletRequest request) throws Exception {

        return "egovframework/com/dty/hnr/vct/VctnManageList";
    }


    /**
     * 휴가 승인 목록 화면 이동
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/dty/hnr/vct/VctnConfmList.do")
    public String vctnConfmList(ModelMap model, HttpServletRequest request) throws Exception {

        return "egovframework/com/dty/hnr/vct/VctnConfmList";
    }


    /**
     * 휴가 관리 목록 조회
     *
     * @param searchVO
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/dty/hnr/vct/VctnManages")
    public ApiResponse<Object> selectVctnManageList(@ModelAttribute VctnManageVO searchVO) throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
        List<String> authList = EgovUserDetailsHelper.getAuthorities();

        // 개인 연차 조회
        YrycManageVO yrycManageVO = new YrycManageVO();

        // "ROLE_ADMIN", "ROLE_MNGM" 권한이 아니면 user_id param 셋팅
        if (authList.stream().noneMatch(authStr -> StringUtils.equalsAny(authStr, "ROLE_ADMIN", "ROLE_MNGM"))) {

            yrycManageVO.setMberId(user.getUniqId());
            searchVO.setApplcntId(user.getUniqId());
        }

        yrycManageVO.setOccrrncYear(EgovDateUtil.getCurrentYearAsString());

        List<YrycManageVO> yrycResultList = yrycManageService.selectYrycManageList(yrycManageVO);

        // 등록된 연차 없음 - 종료
        if (yrycResultList == null || yrycResultList.size() == 0) {
            return ApiResponse.fail(ApiResponseCode.YRYC_NOT_REGIST, null);
        }

        PaginationInfo paginationInfo = new PaginationInfo();
        setUpPaginationInfo(searchVO, paginationInfo);

        // 휴가 목록
        List<VctnManageVO> resultList = vctnManageService.selectVctnManageList(searchVO);
        paginationInfo.setTotalRecordCount(vctnManageService.selectVctnManageListTotCnt(searchVO));

        return ApiResponse.success(new Object[]{resultList}, paginationInfo, ApiResponseCode.READ_SUCCESS.getMessage());
    }




    /**
     * 페이징
     */
    private void setUpPaginationInfo(VctnManageVO searchVO, PaginationInfo paginationInfo) {

        paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
        paginationInfo.setPageSize(searchVO.getPageSize());

        searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
        searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
    }

}

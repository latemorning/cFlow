package egovframework.com.dty.hnr.vct.web;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.CmmnDetailCode;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.dty.hnr.vct.service.VctnManage;
import egovframework.com.cmm.util.ApiResponse;
import egovframework.com.cmm.util.ApiResponseCode;
import egovframework.com.dty.hnr.vct.service.VctnManageService;
import egovframework.com.dty.hnr.vct.service.VctnManageVO;
import egovframework.com.dty.hnr.yrc.service.YrycManageService;
import egovframework.com.dty.hnr.yrc.service.YrycManageVO;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

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

    @Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;

    @Resource(name = "egovMessageSource")
    EgovMessageSource egovMessageSource;

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
     * 휴가 관리 목록 화면 이동
     *
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/dty/hnr/vct/VctnManageList.do")
    public String vctnManageList(ModelMap model, HttpServletRequest request) throws Exception {

        java.util.Calendar cal = java.util.Calendar.getInstance();
        String[] yearList = new String[5];
        for (int x = 0; x < 5; x++) {
            yearList[x] = Integer.toString(cal.get(java.util.Calendar.YEAR) - x);
        }

        model.addAttribute("yearList", yearList);

        return "egovframework/com/dty/hnr/vct/VctnManageList";
    }


    /**
     * 휴가 관리 목록 조회
     *
     * @param searchVO
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/dty/hnr/vct/vctn-manages")
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

        // 현재 년도 set
        yrycManageVO.setOccrrncYear(EgovDateUtil.getCurrentYearAsString());

        // 개인 연차 목록
        List<YrycManageVO> yrycResultList = yrycManageService.selectYrycManageList(yrycManageVO);

        // 등록된 연차 없음 -> 종료
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
     * 휴가 관리 등록 이동
     *
     * @param vctnManage
     * @param vctnManageVO
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/dty/hnr/vct/VctnRegist.do")
    public String insertViewVctnManage(@ModelAttribute("vctnManage") VctnManage vctnManage,
            @ModelAttribute("vctnManageVO") VctnManageVO vctnManageVO, ModelMap model) throws Exception {

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        YrycManageVO yrycManageVO = yrycManageService.selectYrycManage(
                user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));

        yrycManageVO.setApplcntId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
        yrycManageVO.setApplcntNm(user == null ? "" : EgovStringUtil.isNullToString(user.getName()));
        yrycManageVO.setOrgnztNm(user == null ? "" : EgovStringUtil.isNullToString(user.getOrgnztNm()));

        // 공통코드 - 휴가 구분
        ComDefaultCodeVO vo = new ComDefaultCodeVO();
        vo.setCodeId("COM056");
        List<CmmnDetailCode> vctnSeCodeList = cmmUseService.selectCmmCodeDetail(vo);

        model.addAttribute("vctnSeCode", vctnSeCodeList);
        model.addAttribute("yrycManageVO", yrycManageVO);

        return "egovframework/com/dty/hnr/vct/VctnRegist";
    }


    /**
     * 휴가관리 등록
     *
     * @param vctnManage
     * @param vctnManageVO
     * @param status
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping(value = "/dty/hnr/vct/vctn-manage")
    public ApiResponse<Object> insertVcatnManage(@ModelAttribute("vctnManage") VctnManage vctnManage,
            @ModelAttribute("vctnManageVO") VctnManageVO vctnManageVO,
            SessionStatus status, @RequestParam Map<?, ?> commandMap, ModelMap model) throws Exception {

        if (!EgovUserDetailsHelper.isAuthenticated()) {
            return ApiResponse.fail(ApiResponseCode.UNAUTHORIZED, null);
        }

        // 승인권자 소속명, 성명 유지
        model.addAttribute("infSanctnDtNm", commandMap.get("sanctnDtNm") == null ? "" : commandMap.get("sanctnDtNm"));
        model.addAttribute("infOrgnztNm", commandMap.get("orgnztNm") == null ? "" : commandMap.get("orgnztNm"));

        String sEnddeView = commandMap.get("enddeView") == null ? "" : (String) commandMap.get("enddeView"); // 종료일자 구분
        if (!sEnddeView.equals("")) {
            vctnManage.setEndde(sEnddeView);
        }

        String sTemp = null;
        String sTempMessage = null;
        int iTemp = 0;

        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        if (user != null) {

            if (vctnManage.getSanctnerId() != null) {
                vctnManage.setConfmAt("A");
            }
            vctnManage.setApplcntId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
            vctnManage.setFrstRegisterId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));

            vctnManageVO.setApplcntId(user == null ? "" : EgovStringUtil.isNullToString(user.getUniqId()));
            vctnManageVO.setSearchKeyword(EgovStringUtil.removeMinusChar(vctnManage.getBgnde()));

            // 시작일자 포함여부
            iTemp = vctnManageService.selectVctnManageDplctAt(vctnManageVO);
            vctnManageVO.setSearchKeyword(EgovStringUtil.removeMinusChar(vctnManage.getEndde()));

            // 종료일자 포함여부
            iTemp += vctnManageService.selectVctnManageDplctAt(vctnManageVO);

            if (iTemp == 0) {
                // 휴가 등록
                status.setComplete();
                sTemp = vctnManageService.insertVctnManage(vctnManage, vctnManageVO);

                if (sTemp.equals("01")) {
                    // 등록 성공
                    return ApiResponse.success(new Object[]{null}, null, ApiResponseCode.INSERT_SUCCESS.getMessage());
                } else {
                    // 오류
                    ApiResponseCode responseCode;

                    if (sTemp.equals("99")) {
                        responseCode = ApiResponseCode.VCTN_APNTDT_ERROR;
                    } else if (sTemp.equals("09")) {
                        responseCode = ApiResponseCode.VCTN_THAT_YEAR_ONLY;
                    } else if (sTemp.equals("02")) {
                        responseCode = ApiResponseCode.LAKE_VCTN_ERROR;
                    } else if (sTemp.equals("03")) {
                        responseCode = ApiResponseCode.LAKE_VCTN_ERROR;
                    } else {
                        responseCode = ApiResponseCode.INTERNAL_SERVER_ERROR;
                    }

                    return ApiResponse.fail(responseCode, null);
                }
            } else {
                // 중복됨
                return ApiResponse.fail(ApiResponseCode.VCTN_APNTDT_ALREADY_EXIST, null);
            }
        } else {
            // not login
            return ApiResponse.fail(ApiResponseCode.UNAUTHORIZED, null);
        }
    }


    /**
     * 등록된 휴가관리의 상세정보를 조회한다.
     *
     * @param vctnManageVO
     * @param vctnManage
     * @param commandMap
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/dty/hnr/vct/VctnManageDetail.do")
    public String selectVctnManage(@ModelAttribute("vctnManageVO") VctnManageVO vctnManageVO, @ModelAttribute("vctnManage") VctnManage vctnManage, @RequestParam Map<?, ?> commandMap, ModelMap model) throws Exception {

        String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd"); // 상세정보 구분
        vctnManageVO.setBgnde(EgovStringUtil.removeMinusChar(vctnManageVO.getBgnde()));
        vctnManageVO.setEndde(EgovStringUtil.removeMinusChar(vctnManageVO.getEndde()));

        // 등록 상세정보
        VctnManageVO vctnManageVOTemp = vctnManageService.selectVctnManage(vctnManageVO);

        model.addAttribute("vctnManageVO", vctnManageVOTemp);
        model.addAttribute("message", egovMessageSource.getMessage("success.common.select"));

        if (sCmd.equals("updt")) {

            ComDefaultCodeVO vo = new ComDefaultCodeVO();
            vo.setCodeId("COM056");
            List<CmmnDetailCode> vctnSeCodeList = cmmUseService.selectCmmCodeDetail(vo);

            model.addAttribute("vctnSeCode", vctnSeCodeList);
            model.addAttribute("vctnManage", vctnManageVOTemp);

            return "egovframework/com/dty/hnr/vct/VctnUpdt";
        } else {

            return "egovframework/com/dty/hnr/vct/VctnDetail";
        }
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

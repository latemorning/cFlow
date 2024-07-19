package egovframework.com.dty.hnr.vct.web;

import egovframework.com.cmm.EgovComponentChecker;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.dty.hnr.vct.service.VctnManage;
import egovframework.com.cmm.util.ApiResponse;
import egovframework.com.cmm.util.ApiResponseCode;
import egovframework.com.dty.hnr.vct.service.VctnManageService;
import egovframework.com.dty.hnr.vct.service.VctnManageVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
   * 연차 관리 목록 화면 이동
   *
   * @param model
   * @param request
   * @return
   * @throws Exception
   */
  @GetMapping(value = "/dty/hnr/vct/YrycManageList.do")
  public String yrycManageList(ModelMap model, HttpServletRequest request) throws Exception {

    return "egovframework/com/dty/hnr/vct/YrycManageList";
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

//    String searchKeyword = searchVO.getSearchKeyword();


    LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();


//    vctnManageVO = egovVcatnManageService.selectIndvdlYrycManage(user.getUniqId());

//    if (vcatnManageVO == null) {
//      model.addAttribute("messageTemp",
//          egovMessageSource.getMessage("comUssIonVct.vcatnManageList.validate.move")); // 휴가 사용을 위한 개인연차 등록을 위해 개인연차관리 콤포넌트로 이동
//      return "egovframework/com/uss/ion/yrc/EgovIndvdlYrycManageList";
//    } else {

//      vcatnManageVO.setSearchKeyword(searchKeyword);

      PaginationInfo paginationInfo = new PaginationInfo();
      setUpPaginationInfo(searchVO, paginationInfo);

      /** paging */

//      model.addAttribute("vcatnManageVO", vcatnManageVO);


      searchVO.setApplcntId(user.getUniqId());
      // 여기 구현
//      searchVO.setVcatnManageList(egovVcatnManageService.selectVcatnManageList(vcatnManageVO));
    searchVO.setVctnManageList(vctnManageService.selectVctnManageList(searchVO));


      int totCnt = vctnManageService.selectVctnManageListTotCnt(searchVO);

      paginationInfo.setTotalRecordCount(totCnt);




    return ApiResponse.success(new Object[]{searchVO}, paginationInfo, ApiResponseCode.READ_SUCCESS.getMessage());

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

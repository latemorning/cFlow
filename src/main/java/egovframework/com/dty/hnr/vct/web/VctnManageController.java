package egovframework.com.dty.hnr.vct.web;

import egovframework.com.dty.hnr.vct.service.VctnManage;
import egovframework.com.cmm.util.ApiResponse;
import egovframework.com.cmm.util.ApiResponseCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * VctnManageController
 *
 * @author KTH
 * @since 24. 7. 15.
 **/
@RestController
public class VctnManageController {


  /**
   * 조회 샘플
   *
   * @param itemSeq
   * @return
   * @throws Exception
   */
  @GetMapping(value = "/dty/hnr/vct/vctnManageTest/{itemSeq}")
  public ApiResponse<Object> vctnManageItem(@PathVariable Integer itemSeq) throws Exception {

    VctnManage vctnManage = new VctnManage();

    vctnManage.setBgnde("20240722");
    vctnManage.setEndde("20240722");

    return ApiResponse.success(new Object[]{vctnManage}, ApiResponseCode.READ_SUCCESS.getMessage());
  }



}

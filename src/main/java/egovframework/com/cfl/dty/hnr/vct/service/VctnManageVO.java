package egovframework.com.cfl.dty.hnr.vct.service;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VctnManageVO
 *
 * <p>
 * 휴가 관리 VO
 * </p>
 *
 * @author KTH
 * @since 24. 7. 15.
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VctnManageVO extends VctnManage implements Serializable {

  // 휴가 목록
  List<VctnManageVO> vctnManageList;

  //  신청자명
  private String applcntNm;

  //  승인자명
  private String sanctnerNm;

  //  휴가구분명
  private String vcatnSeNm;

  //  사용자ID
  private String usid;

  //  발생연차개수
  private double occrncYrycCo = 0.0;

  //  사용연차개수
  private double useYrycCo = 0.0;

  //  잔여연차개수
  private double remndrYrycCo = 0.0;

  //  승인자 소속명
  private String sanctnerOrgnztNm;

  //  검색 연도
  private String searchYear;

  //  검색 월
  private String searchMonth;

  //  검색 성명
  private String searchNm;

  //  검색 진행구분
  private String searchConfmAt;

  //  sTempBgnde
  private String tempBgnde;

  //  sTempEndde
  private String tempEndde;

  //  tempUsNm
  private String tempUsNm;

  //  tempOrgnztNm
  private String tempOrgnztNm;

  //  신청자ID
  private String applcntIdKey;

  //  휴가구분
  private String vcatnSeKey;

  //  시작일자
  private String bgndeKey;

  //  종료일자
  private String enddeKey;

}

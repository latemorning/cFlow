package egovframework.com.cfl.dty.hnr.vct.service;

import egovframework.com.cmm.ComDefaultVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VctnManage
 *
 * <p>
 * 휴가 관리 model
 * </p>
 *
 * @author KTH
 * @since 24. 7. 15.
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VctnManage extends ComDefaultVO {

  private String applcntId; // 신청자ID

  private String vcatnSe; // 휴가구분

  private String bgnde; // 시작일자

  private String endde; // 종료일자

  private String reqstDe; // 신청일자

  private String vcatnResn; // 휴가사유

  private String occrrncYear; // 발생연도

  private String noonSe; // 정오구분

  private String sanctnerId; // 결재자ID

  private String confmAt; // 승인여부

  private String sanctnDt; // 결재일시

  private String returnResn; // 반려사유

  private String infrmlSanctnId; // 약식결재ID

  private String frstRegisterId; // 최초등록자ID

  private String frstRegisterPnttm; // 최초등록시점

  private String lastUpdusrId; // 최종수정자ID

  private String lastUpdusrPnttm; // 최종수정시점

  private String sanctnDtNm; // sanctnDtNm

  private String orgnztNm; // 조직명
}

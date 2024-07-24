package egovframework.com.dty.hnr.yrc.service;

import egovframework.com.cmm.ComDefaultVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * YrycManage
 *
 * <p>
 * 개인 연차 관리 model
 * </p>
 *
 * @author KTH
 * @since 24. 7. 23.
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YrycManage extends ComDefaultVO {

  private static final long serialVersionUID = 1L;

  private String occrrncYear;  // 발생연도

  private String mberId;  // 사용자ID

  private String mberNm;  // 사용자 이름

  private double occrncYrycCo;  // 발생연차개수

  private double useYrycCo;  // 사용연차개수

  private double remndrYrycCo;  // 잔여연차개수

  private String frstRegisterId;  // 최초등록자ID

  private String frstRegisterPnttm;  // 최초등록시점

  private String lastUpdusrId;  // 최종수정자ID

  private String lastUpdusrPnttm;  // 최종수정시점

}

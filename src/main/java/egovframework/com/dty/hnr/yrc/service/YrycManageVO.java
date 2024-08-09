package egovframework.com.dty.hnr.yrc.service;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * YrycManageVO
 *
 * <p>
 * 개인 연차 관리 VO
 * </p>
 *
 * @author KTH
 * @since 24. 7. 23.
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YrycManageVO extends YrycManage implements Serializable {

  private static final long serialVersionUID = 1L;

  private String applcntId;

  private String applcntNm;

  private String orgnztNm;

  private String usid;

}

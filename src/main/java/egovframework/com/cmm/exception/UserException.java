package egovframework.com.cmm.exception;

import egovframework.com.cmm.util.ApiResponseCode;

public class UserException extends BaseException {

  public UserException(ApiResponseCode responseCode) {
    super(responseCode);
  }
}

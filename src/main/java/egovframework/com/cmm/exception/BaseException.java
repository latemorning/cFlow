package egovframework.com.cmm.exception;

import egovframework.com.cmm.util.ApiResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

  private final ApiResponseCode responseCode;

  @Override
  public String getMessage() {
    return responseCode.getMessage();
  }
}



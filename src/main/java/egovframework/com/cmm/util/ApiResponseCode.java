package egovframework.com.cmm.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiResponseCode {

  // 400 Bad Request
  BAD_REQUEST(HttpStatus.BAD_REQUEST, false, "잘못된 요청입니다."),

  // 403 Forbidden
  FORBIDDEN(HttpStatus.FORBIDDEN, false, "권한이 없습니다."),

  // 404 Not Found
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, false, "요청을 찾을 수 없습니다."),
  // 405 Method Not Allowed
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, false, "허용되지 않은 메소드입니다."),

  // 409 Conflict
  USER_ALREADY_EXIST(HttpStatus.CONFLICT, false, "이미 가입한 사용자입니다."),
  USER_NAME_ALREADY_EXIST(HttpStatus.CONFLICT, false, "이미 존재하는 닉네임입니다."),

  // 500 Internal Server Error
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, false, "서버에 오류가 발생하였습니다."),

  // 업무로직
  YRYC_NOT_REGIST(HttpStatus.INTERNAL_SERVER_ERROR, false, "등록된 연차가 없습니다."),


  // 200 OK
  READ_SUCCESS(HttpStatus.OK, true, "조회 성공"),
  UPDATE_SUCCESS(HttpStatus.OK, true, "수정 성공"),
  LOGIN_SUCCESS(HttpStatus.OK, true, "로그인 성공"),

  // 201 Created
  CREATE_SUCCESS(HttpStatus.CREATED, true, "생성 성공"),
  ;

  private final HttpStatus httpStatus;
  private final Boolean success;
  private final String message;

  public int getHttpStatusCode() {
    return httpStatus.value();
  }

}

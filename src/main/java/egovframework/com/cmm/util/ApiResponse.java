package egovframework.com.cmm.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<E> {

  private ApiHeader header;
  private E[] datas;
  private String msg;

  private static final int SUCCESS = 200;

  private ApiResponse(ApiHeader header, E[] datas, String msg) {
    this.header = header;
    this.datas = datas;
    this.msg = msg;
  }

  public static <E> ApiResponse<E> success(E[] datas, String message) {
    return new ApiResponse<>(new ApiHeader(SUCCESS, "SUCCESS"), datas, message);
  }

  public static <E> ApiResponse<E> fail(ApiResponseCode responseCode, E[] datas) {
    return new ApiResponse<>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getMessage()), datas,
        responseCode.getMessage());
  }
}

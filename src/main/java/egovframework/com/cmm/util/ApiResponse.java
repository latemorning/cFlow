package egovframework.com.cmm.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Getter
@RequiredArgsConstructor
public class ApiResponse<E> {

  private ApiHeader header;
  private E[] datas;
  private PaginationInfo paginationInfo;
  private String msg;

  private static final int SUCCESS = 200;

  private ApiResponse(ApiHeader header, E[] datas, PaginationInfo paginationInfo, String msg) {
    this.header = header;
    this.datas = datas;
    this.paginationInfo = paginationInfo;
    this.msg = msg;
  }

  public static <E> ApiResponse<E> success(E[] datas, PaginationInfo paginationInfo, String message) {
    return new ApiResponse<>(new ApiHeader(SUCCESS, "SUCCESS"), datas, paginationInfo, message);
  }

  public static <E> ApiResponse<E> fail(ApiResponseCode responseCode, E[] datas) {
    return new ApiResponse<>(new ApiHeader(responseCode.getHttpStatusCode(), responseCode.getMessage()), datas, null,
        responseCode.getMessage());
  }
}

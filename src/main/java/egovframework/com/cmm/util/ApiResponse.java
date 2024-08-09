package egovframework.com.cmm.util;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private ApiHeader header;
    private T datas;
    private PaginationInfo paginationInfo;
    private String msg;

    private static final int SUCCESS = 200;

    private ApiResponse(ApiHeader header, T datas, PaginationInfo paginationInfo, String msg) {
        this.header = header;
        this.datas = datas;
        this.paginationInfo = paginationInfo;
        this.msg = msg;
    }

    public static <T> ApiResponse<T> success() {
        return success(null, null, "SUCCESS");
    }

    public static <T> ApiResponse<T> success(T datas, PaginationInfo paginationInfo, String msg) {
        return ApiResponse.<T>builder()
                .header(new ApiHeader(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()))
                .datas(datas)
                .paginationInfo(paginationInfo)
                .msg(msg).build();
    }


    public static <T> ApiResponse<T> fail() {
//        return new ApiResponse<>(new ApiHeader(responseCode.getHttpStatusCode(), "FAIL"), datas, null,
//                responseCode.getMessage());
        return fail();

    }

    public static <T> ApiResponse<T> fail(ApiResponseCode responseCode, T datas) {
//        return new ApiResponse<>(new ApiHeader(responseCode.getHttpStatusCode(), "FAIL"), datas, null,
//                responseCode.getMessage());

       return ApiResponse.<T>builder()
               .header(new ApiHeader(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase()))
               .datas(datas)
               .paginationInfo(null)
               .msg("").build();

    }

}

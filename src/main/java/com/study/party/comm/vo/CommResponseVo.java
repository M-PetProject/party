package com.study.party.comm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

import static com.study.party.comm.util.DateUtil.getFullTime;
import static com.study.party.comm.util.StringUtil.isEmptyObj;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommResponseVo {

    Object body;

    CommResultVo resultVo;

    public ResponseEntity toResponseEntity() {
        if ( isEmptyObj(resultVo) ) {
            return ResponseEntity.status(200)
                                 .header("res_tm", getFullTime())
                                 .body(body);
        }
        return ResponseEntity.status(resultVo.getCode())
                             .header("res_tm", getFullTime())
                             .body( isEmptyObj(resultVo.getData()) ? resultVo.getMsg() : resultVo.getData() );
    }

    /**
     * 200 : 정상
     * @return
     */
    public ResponseEntity ok() {
        return ResponseEntity.status(200)
                             .header("res_tm", getFullTime())
                             .body(body);
    }

    /**
     * 400 : 잘못된 문법으로 인하여 서버가 요청하여 이해할 수 없음을 의미합니다. <br>
     *       필수입력값 오류 <br>
     *       유효성 검사 오류 <br>
     *       사용자 입력값이 오류가 발생하였을 때 사용 <br>
     * @return
     */
    public ResponseEntity badRequest() {
        return ResponseEntity.status(400)
                             .header("res_tm", getFullTime())
                             .body(body);
    }

    /**
     * 401 : 비인증 <br>
     *       인증을 받지 않은 사용자가 기능을 요청할 경우 <br>
     * @return
     */
    public ResponseEntity unauthorized() {
        return ResponseEntity.status(401)
                .header("res_tm", getFullTime())
                .body(body);
    }

    /**
     * 404 : 리소스를 찾지 못하는 경우
     * @return
     */
    public ResponseEntity notFound() {
        return ResponseEntity.status(404)
                             .header("res_tm", getFullTime())
                             .body(body);
    }

    /**
     * 500 : 서버 에러
     * @return
     */
    public ResponseEntity internalServerError() {
        return ResponseEntity.status(500)
                             .header("res_tm", getFullTime())
                             .body(body);
    }

}

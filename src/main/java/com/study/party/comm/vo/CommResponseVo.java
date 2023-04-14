package com.study.party.comm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.ResponseEntity;

import static com.study.party.comm.util.DateUtil.getFullTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommResponseVo {

    Object body;

    public ResponseEntity ok() {
        return ResponseEntity.status(200)
                             .header("res_tm", getFullTime())
                             .body(body);
    }
    public ResponseEntity badRequest() {
        return ResponseEntity.status(400)
                             .header("res_tm", getFullTime())
                             .body(body);
    }
    public ResponseEntity Unauthorized() {
        return ResponseEntity.status(401)
                .header("res_tm", getFullTime())
                .body(body);
    }
    public ResponseEntity notFound() {
        return ResponseEntity.status(404)
                             .header("res_tm", getFullTime())
                             .body(body);
    }
    public ResponseEntity internalServerError() {
        return ResponseEntity.status(500)
                             .header("res_tm", getFullTime())
                             .body(body);
    }

}

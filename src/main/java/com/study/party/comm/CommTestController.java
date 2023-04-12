package com.study.party.comm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommTestController {

    private final CommTestService commTestService;

    @Operation(summary = "테스트 메서드", description = "select 1 을 수행하고 Map을 리턴합니다")
    @ApiResponses({
        @ApiResponse(responseCode="200", description="OK", content=@Content(schema=@Schema(implementation= Map.class))),
        @ApiResponse(responseCode="400", description="BAD Request", content=@Content(schema=@Schema(implementation= Map.class))),
        @ApiResponse(responseCode="404", description="NOT FOUND", content=@Content(schema=@Schema(implementation= Map.class))),
        @ApiResponse(responseCode="500", description="INTERNAL SERVER ERROR", content=@Content(schema=@Schema(implementation= Map.class))),
    })
    @GetMapping("/comm/test")
    public ResponseEntity getTest() {
        return ResponseEntity.ok(commTestService.getTest());
    }

}

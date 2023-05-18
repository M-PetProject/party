package com.study.party.vote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.study.party.vote.vo.VoteListInfo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@Api(tags = {"투표 API"})
public class VoteController {
    @Operation(summary = "투표대상인 장소목록 조회  API", description = "특정회식에 등록된 투표대상 장소목록을 가져온다")
    @GetMapping("/vote/places")
    public ResponseEntity<List<VoteListInfo>> getListOfPlaceForVote(HttpServletRequest request
            , @Parameter(name="vote_idx" , required=true, description="투표정보 idx") long vote_idx){
        return getDummyDataForVoteList(vote_idx, VoteListInfo.class);
    }

    private <T> ResponseEntity<List<T>> getDummyDataForVoteList(long vote_idx, Class<T> prmClass) {
        List<T> resultList = IntStream.range(1, 10)
                                        .mapToObj(p -> new ObjectMapper().convertValue(ImmutableMap.<String,Object>builder()
                                                                                                .put("voteIdx", vote_idx)
                                                                                                .build()
                                                                                    , prmClass))
                                        .collect(Collectors.toList());
        return new ResponseEntity(resultList, HttpStatus.OK);
    }
}

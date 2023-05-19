package com.study.party.vote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.study.party.vote.vo.VoteMember;
import com.study.party.vote.vo.VotePlaceInfo;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Tag(name="vote-controller", description="투표 조회 및 처리API")
@RestController
@RequiredArgsConstructor
public class VoteController {
    @Operation(summary = "투표대상인 장소목록 조회  API", description = "특정회식에 등록된 투표대상 장소목록을 가져온다")
    @GetMapping("/vote/places")
    public ResponseEntity<List<VotePlaceInfo>> getListOfPlaceForVote(HttpServletRequest request
            , @Parameter(name="vote_idx" , required=true, description="투표정보 idx") long vote_idx){
        return getDummyDataForVoteList(vote_idx, VotePlaceInfo.class);
    }

    private <T> ResponseEntity<List<T>> getDummyDataForVoteList(long vote_idx, Class<T> prmClass) {
        List<String> placeDummyList = ImmutableList.<String>builder()
                                                    .add ("좋다게")
                                                    .add ("망원회관")
                                                    .add ("호남돼지")
                                                    .add ("계륵장군")
                                                    .build();
        List<String> personDummyList = ImmutableList.<String>builder()
                .add ("수은")
                .add ("베니")
                .add ("제이크")
                .add ("스패로")
                .add ("엘리")
                .add ("카터")
                .add ("로지")
                .build();

        Random random = new Random();
        ObjectMapper om = new ObjectMapper();
        List<T> resultList = IntStream
                                .range(1, 10)
                                .mapToObj(p -> om.convertValue(ImmutableMap.<String,Object>builder()
                                                                            .put("voteIdx", vote_idx)
                                                                            .put("placeNm", placeDummyList.get(random.nextInt(placeDummyList.size())))
                                                                            .put("voteMemberList", IntStream
                                                                                                    .range(0, random.nextInt(personDummyList.size()))
                                                                                                    .mapToObj(pPersonIdx -> om.convertValue(ImmutableMap.<String,Object>builder()
                                                                                                                                                        .put("name"     , personDummyList.get(pPersonIdx))
                                                                                                                                                        .put("voteDt"   , new SimpleDateFormat("yyyyMMdd").format(new Date()))
                                                                                                                                                        .build()
                                                                                                                                            , VoteMember.class))
                                                                                                    .collect(Collectors.toList()))
                                                                            .build(), prmClass))
                                .collect(Collectors.toList());
        return new ResponseEntity(resultList, HttpStatus.OK);
    }
}

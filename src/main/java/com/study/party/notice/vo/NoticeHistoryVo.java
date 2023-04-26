package com.study.party.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="공지사항 좋아요/싫어요 이력 VO")
public class NoticeHistoryVo {
    private long noticeIdx;
    private long memberIdx;

}

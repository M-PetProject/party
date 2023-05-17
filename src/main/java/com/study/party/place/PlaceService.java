package com.study.party.place;

import com.study.party.comm.comment.CommCommentService;
import com.study.party.comm.comment.vo.CommCommentVo;
import com.study.party.comm.util.StringUtil;
import com.study.party.comm.vo.CommResultVo;
import com.study.party.exception.InternalServerErrorException;
import com.study.party.jpa.entity.place.PlaceEntity;
import com.study.party.jpa.repository.place.PlaceRepository;
import com.study.party.member.MemberService;
import com.study.party.member.vo.MemberVo;
import com.study.party.place.dto.PlaceDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final MemberService memberService;
    private final CommCommentService commCommentService;

    @Transactional
    public List<PlaceDto> getPlaces() {
        ModelMapper modelMapper = new ModelMapper();
        List<PlaceEntity> placeEntityList = placeRepository.findAllByDeleteYn("N");

        List<PlaceDto> placeDtoList = placeEntityList.stream()
                .map(entity -> {
                    PlaceDto placeDto = modelMapper.map(entity, PlaceDto.class);
                    MemberVo member = memberService.getMember(placeDto.getCreatorMemberIdx());
                    if(member != null) {
                        placeDto.setCreatorMemberName(member.getMemberName());
                    }
                    int commentCount = commCommentService.getCommentsTotCnt(
                            CommCommentVo.builder()
                                    .commentCd("PLAC")
                                    .postIdx(placeDto.getPlaceBasicInfoIdx())
                                    .build()
                    );
                    placeDto.setCommentCount(commentCount);
                    return placeDto;
                })
                .collect(Collectors.toList());

        return placeDtoList;
    }

    @Transactional
    public PlaceDto getPlace(Long idx) {
        Optional<PlaceEntity> placeEntity = placeRepository.findById(idx);


        if(placeEntity.isEmpty()) throw new InternalServerErrorException("장소 정보가 없습니다.");

        ModelMapper modelMapper = new ModelMapper();
        PlaceDto placeDto = modelMapper.map(placeEntity.get(), PlaceDto.class);
        MemberVo member = memberService.getMember(placeDto.getCreatorMemberIdx());
        if(member != null) {
            placeDto.setCreatorMemberName(member.getMemberName());
        }
        int commentCount = commCommentService.getCommentsTotCnt(
                CommCommentVo.builder()
                        .commentCd("PLAC")
                        .postIdx(placeDto.getPlaceBasicInfoIdx())
                        .build()
        );
        placeDto.setCommentCount(commentCount);

        return placeDto;
    }

    @Transactional
    public CommResultVo createPlace(PlaceDto placeDto) {
        if(StringUtil.isEmptyObj(placeDto.getPublicYn())) {
            placeDto.setPublicYn("Y");
        }
        if(StringUtil.isEmptyObj(placeDto.getDeleteYn())) {
            placeDto.setDeleteYn("N");
        }

        PlaceEntity placeEntity = new ModelMapper().map(placeDto, PlaceEntity.class);
        placeRepository.save(placeEntity);
        return CommResultVo.builder().code(200).msg("장소가 등록되었습니다.").build();
    }

    @Transactional
    public CommResultVo updatePlace(PlaceDto placeDto, long userIdx) {
        Optional<PlaceEntity> place = placeRepository.findById(placeDto.getPlaceBasicInfoIdx());
        if(place.isEmpty()) {
            throw new InternalServerErrorException("정보 수정에 실패했습니다.");
        }
        if(place.get().getCreatorMemberIdx() != userIdx) {
            throw new InternalServerErrorException("장소수정은 작성자만 가능합니다.");
        }

        PlaceEntity placeEntity = new ModelMapper().map(placeDto, PlaceEntity.class);
        placeRepository.save(placeEntity);
        return CommResultVo.builder().code(200).msg("장소가 수정되었습니다.").build();
    }

    @Transactional
    public CommResultVo deletePlace(long placeBasicInfoIdx, long userIdx) {
        Optional<PlaceEntity> placeEntity = placeRepository.findById(placeBasicInfoIdx);
        if(placeEntity.isEmpty()) {
            throw new InternalServerErrorException("장소 삭제에 실패했습니다.");
        }
        if(placeEntity.get().getCreatorMemberIdx() != userIdx) {
            throw new InternalServerErrorException("장소삭제는 작성자만 가능합니다.");
        }

        placeEntity.get().setDeleteYn("Y");
//        placeRepository.save(placeEntity);

        return CommResultVo.builder().code(200).msg("장소가 삭제되었습니다.").build();
    }

}

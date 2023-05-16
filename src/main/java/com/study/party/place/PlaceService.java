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
import com.study.party.place.vo.PlaceVo;
import lombok.AllArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
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
    public List<PlaceVo> getPlaces() {
        ModelMapper modelMapper = new ModelMapper();
        List<PlaceEntity> placeEntityList = placeRepository.findAll();

        List<PlaceVo> placeVoList = placeEntityList.stream()
                .map(entity -> {
                    PlaceVo placeVo = modelMapper.map(entity, PlaceVo.class);
                    MemberVo member = memberService.getMember(placeVo.getCreatorMemberIdx());
                    if(member != null) {
                        placeVo.setCreatorMemberName(member.getMemberName());
                    }

                    return placeVo;
                })
                .collect(Collectors.toList());

        return placeVoList;
    }

    @Transactional
    public PlaceVo getPlace(Long idx) {
        Optional<PlaceEntity> placeEntity = placeRepository.findById(idx);


        if(placeEntity.isEmpty()) throw new InternalServerErrorException("장소 정보가 없습니다.");

        ModelMapper modelMapper = new ModelMapper();
        PlaceVo placeVo = modelMapper.map(placeEntity.get(), PlaceVo.class);
        MemberVo member = memberService.getMember(placeVo.getCreatorMemberIdx());
        if(member != null) {
            placeVo.setCreatorMemberName(member.getMemberName());
        }
        int commentCount = commCommentService.getCommentsTotCnt(
                CommCommentVo.builder()
                        .commentCd("PLAC")
                        .postIdx(placeVo.getPlaceBasicInfoIdx())
                        .build()
        );
        placeVo.setCommentCount(commentCount);

        return placeVo;
    }

    @Transactional
    public CommResultVo createPlace(PlaceEntity placeEntity) {
        if(StringUtil.isEmptyObj(placeEntity.getPublicYn())) {
            placeEntity.setPublicYn("Y");
        }
        if(StringUtil.isEmptyObj(placeEntity.getDeleteYn())) {
            placeEntity.setDeleteYn("Y");
        }

        placeRepository.save(placeEntity);
        return CommResultVo.builder().code(200).msg("장소가 등록되었습니다.").build();
    }

    @Transactional
    public CommResultVo updatePlace(PlaceEntity placeEntity) {
        placeRepository.save(placeEntity);
        return CommResultVo.builder().code(200).msg("장소가 수정되었습니다.").build();
    }
    @Transactional
    public CommResultVo deletePlace(PlaceEntity placeEntity) {
        Optional<PlaceEntity> place = placeRepository.findById(placeEntity.getPlaceBasicInfoIdx());
        if(!place.isEmpty()) {
            placeRepository.delete(place.get());
        } else {
            throw new InternalServerErrorException("장소 삭제에 실패했습니다.");
        }

        return CommResultVo.builder().code(200).msg("장소가 삭제되었습니다.").build();
    }
}

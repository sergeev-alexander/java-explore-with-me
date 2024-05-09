package alexander.sergeev.mapper;

import alexander.sergeev.dto.HitDto;
import alexander.sergeev.model.Hit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HitMapper {

    public Hit mapIncomingDtoToHit(HitDto hitDto) {
        return new Hit(
                null,
                null,
                hitDto.getUri(),
                hitDto.getIp(),
                hitDto.getTimestamp());
    }

    public HitDto mapHitToDto(Hit hit) {
        return new HitDto(
                hit.getId(),
                hit.getApp().getName(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp());
    }

}

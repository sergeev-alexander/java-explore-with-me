package alexander.sergeev.mapper;

import alexander.sergeev.dto.HitDto;
import alexander.sergeev.model.Hit;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class HitMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Hit mapIncomingDtoToHit(HitDto hitDto) {
        return new Hit(
                null,
                null,
                hitDto.getUri(),
                hitDto.getIp(),
                LocalDateTime.parse(hitDto.getTimestamp(), formatter));
    }

    public HitDto mapHitToDto(Hit hit) {
        return new HitDto(
                hit.getId(),
                hit.getApp().getName(),
                hit.getUri(),
                hit.getIp(),
                hit.getTimestamp().format(formatter));
    }

}

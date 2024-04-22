package alexander.sergeev.service;

import alexander.sergeev.dto.HitDto;
import alexander.sergeev.dto.StatsDto;
import alexander.sergeev.mapper.HitMapper;
import alexander.sergeev.model.App;
import alexander.sergeev.model.Hit;
import alexander.sergeev.repository.AppRepository;
import alexander.sergeev.repository.HitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HitService {

    private final HitRepository hitRepository;

    private final AppRepository appRepository;

    public HitDto postHit(HitDto hitDto) {
        Hit hit = HitMapper.mapIncomingDtoToHit(hitDto);
        String appName = hitDto.getApp();
        hit.setApp(appRepository.findFirstByName(appName)
                .orElse(appRepository.save(new App(null, appName))));
        return HitMapper.mapHitToDto(hitRepository.save(hit));
    }

    public List<StatsDto> getStats(LocalDateTime start,
                                   LocalDateTime end,
                                   List<String> uris,
                                   Boolean unique) {
        if (unique) {
            return uris == null || uris.isEmpty()
                    ? hitRepository.getUniqueStats(start, end)
                    : hitRepository.getUniqueStatsByUris(start, end, uris);
        } else {
            return uris == null || uris.isEmpty()
                    ? hitRepository.getStats(start, end)
                    : hitRepository.getStatsByUris(start, end, uris);
        }
    }

}

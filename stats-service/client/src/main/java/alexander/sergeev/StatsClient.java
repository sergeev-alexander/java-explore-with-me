package alexander.sergeev;

import alexander.sergeev.dto.HitDto;
import alexander.sergeev.dto.StatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StatsClient {

    private final RestTemplate restTemplate;
    private static final String hitPath = "/hit";
    private static final String statsPath = "/stats";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String addressBaseUrl, RestTemplateBuilder builder) {
        this.restTemplate = builder
                .rootUri(addressBaseUrl)
                .build();
    }

    public HitDto postHit(HitDto hitDto) {
        return restTemplate.exchange(hitPath,
                HttpMethod.POST, new HttpEntity<>(hitDto), HitDto.class).getBody();
    }

    public List<StatsDto> getStats(LocalDateTime start,
                                             LocalDateTime end,
                                             List<String> uris,
                                             Boolean unique) {
        StringBuilder stringBuilder = new StringBuilder(statsPath + "?start={start}&end={end}");
        Map<String, String> params = new HashMap<>(4);
        params.put("start", start.format(formatter));
        params.put("end", end.format(formatter));
        if (!uris.isEmpty()) {
            stringBuilder.append("&uris={uris}");
            params.put("uris", String.join(",", uris));
        }
        if (unique) stringBuilder.append("&unique=true");
        StatsDto[] array = restTemplate.getForObject(stringBuilder.toString(), StatsDto[].class, params);
        return new ArrayList<>(Arrays.asList(array));
    }

}

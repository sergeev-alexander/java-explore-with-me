package alexander.sergeev.repository;

import alexander.sergeev.dto.StatsDto;
import alexander.sergeev.model.Hit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT new alexander.sergeev.dto.StatsDto(a.name, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit as h " +
            "INNER JOIN App as a ON h.app = a.id " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY a.name, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<StatsDto> getUniqueStats(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new alexander.sergeev.dto.StatsDto(a.name, h.uri, COUNT(DISTINCT h.ip)) " +
            "FROM Hit as h " +
            "INNER JOIN App as a ON h.app = a.id " +
            "WHERE (h.timestamp BETWEEN ?1 AND ?2) " +
            "AND h.uri IN (?3) " +
            "GROUP BY a.name, h.uri " +
            "ORDER BY COUNT(DISTINCT h.ip) DESC")
    List<StatsDto> getUniqueStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new alexander.sergeev.dto.StatsDto(a.name, h.uri, COUNT(h.ip)) " +
            "FROM Hit as h " +
            "INNER JOIN App as a ON h.app = a.id " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY a.name, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<StatsDto> getStats(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new alexander.sergeev.dto.StatsDto(a.name, h.uri, COUNT(h.ip)) " +
            "FROM Hit as h " +
            "INNER JOIN App as a ON h.app = a.id " +
            "WHERE (h.timestamp BETWEEN ?1 AND ?2) " +
            "AND h.uri IN (?3) " +
            "GROUP BY a.name, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<StatsDto> getStatsByUris(LocalDateTime start, LocalDateTime end, List<String> uris);

}

package alexander.sergeev.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static alexander.sergeev.formatter.FormatterDateTime.DATE_TIME_PATTERN;

@Entity
@Table(name = "hits")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", nullable = false)
    private App app;

    @Column(name = "uri", nullable = false, length = 32)
    private String uri;

    @Column(name = "ip", nullable = false, length = 15)
    private String ip;

    @Column(name = "time_stamp")
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestamp;

}

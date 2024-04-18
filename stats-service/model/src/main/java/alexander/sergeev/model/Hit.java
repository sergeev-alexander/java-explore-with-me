package alexander.sergeev.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "app_id", nullable = false)
    private App app;

    @Column(name = "uri", nullable = false, length = 128)
    private String uri;

    @Column(name = "ip", nullable = false, length = 32)
    private String ip;

    @Column(name = "time_stamp")
    private LocalDateTime timestamp;

}

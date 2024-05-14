package alexander.sergeev.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "compilations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "pinned", nullable = false)
    private Boolean pinned;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilations_id"),
            inverseJoinColumns = @JoinColumn(name = "events_id"))
    private Set<Event> events = new HashSet<>();

}

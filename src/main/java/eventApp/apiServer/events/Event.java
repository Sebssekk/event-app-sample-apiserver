package eventApp.apiServer.events;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

/*
NOTE. Validations and any other DB details
like columns, will not work until a DB will be
connected !! - Can not use them with mock DB
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@AttributeOverrides({
        @AttributeOverride(
                name = "id",
                column = @Column(updatable = false)
        ),
        @AttributeOverride(
                name = "description",
                column = @Column(columnDefinition = "TEXT")
        )
})
public class Event {
    @SequenceGenerator(
            name="event_sequence",
            sequenceName = "event_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "event_sequence"
    )
    @Id
    private Long id;
    @NotNull
    @NotBlank
    private String title;
    private String description;
    @NotNull
    @PastOrPresent
    @NotBlank
    private LocalDateTime dateTime;
    @NotBlank
    private String place;
    @NotNull
    @NotBlank
    private Severity severity;
    @NotBlank
    private String author;
}

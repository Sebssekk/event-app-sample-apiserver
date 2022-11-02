package eventApp.apiServer.events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
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
    private String title;
    private String description;
    @NotNull
    private LocalDateTime dateTime;
    private String place;
    @NotNull
    private Severity severity;
    private String author;

    public Event(String title, String description, LocalDateTime dateTime, String place, Severity severity, String author) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.place = place;
        this.severity = severity;
        this.author = author;
    }
}

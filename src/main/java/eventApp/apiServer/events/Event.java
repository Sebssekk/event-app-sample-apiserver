package eventApp.apiServer.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private String place;
    private Severity severity;
    private String author;
}

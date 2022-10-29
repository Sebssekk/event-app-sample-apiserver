package eventApp.apiServer.events;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class MockEventRepo implements EventRepository{
    @Override
    public List<Event> findAll() {
        return List.of(
                Event.builder()
                        .id(1L)
                        .author("Ben")
                        .title("Error in server room")
                        .place("Server Room")
                        .description("An error occurred in server room and everython g is burning now. I'm super scared about it, need help!")
                        .dateTime(LocalDateTime.of(2022,12,2,12,23))
                        .severity(Severity.ALARM)
                        .build(),
                Event.builder()
                        .id(2L)
                        .author("John")
                        .title("Clean completed")
                        .place("2nd floor")
                        .description("Cleaning operation was successfully completed at second floor, now moving at 3rd")
                        .dateTime(LocalDateTime.of(2022,10,1,9,17))
                        .severity(Severity.SUCCESS)
                        .build(),
                Event.builder()
                        .id(3L)
                        .author("Susan")
                        .title("Migration completed")
                        .place("Datacenter 3")
                        .description("Vm migration was successfully completed from datacenter 3 to datacenter 4")
                        .dateTime(LocalDateTime.of(2022,2,5,18,05))
                        .severity(Severity.SUCCESS)
                        .build(),
                Event.builder()
                        .id(4L)
                        .author("Andrea")
                        .title("Package delivered")
                        .place("Entrance")
                        .description("A package was delivered at entrance. Someone should go there and check")
                        .dateTime(LocalDateTime.of(2022,6,29,14,16))
                        .severity(Severity.INFO)
                        .build(),
                Event.builder()
                        .id(5L)
                        .author("Luis")
                        .title("Running out of Storage resources")
                        .place("Datacenter 1")
                        .description("We are running out of resources in datacenter 1, we should consider to buy more storage resources")
                        .dateTime(LocalDateTime.of(2022,8,10,19,01))
                        .severity(Severity.WARNING)
                        .build());

    }

    @Override
    public Event getEventById(Long id) {
        return null;
    }

    @Override
    public void save(Event event) {

    }

    @Override
    public void saveAll(List<Event> events) {

    }

    @Override
    public void deleteEventById(Long id) {

    }
}

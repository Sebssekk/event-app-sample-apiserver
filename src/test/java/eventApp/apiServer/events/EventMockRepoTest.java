package eventApp.apiServer.events;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EventMockRepoTest {
    @Autowired
    private MockEventRepo eventRepo;

    @Autowired
    private ApplicationContext context;


    @Test
    void should_find_all_starting_events() {
        List<Event> me = (List<Event>) context.getBean("mockEvents");
        List<Event> allEv = eventRepo.findAll();
        Assertions.assertEquals(allEv.size(), me.size());
    }

    @Test
    void should_find_event_by_id() {
        Optional<Event> ev_1 = eventRepo.findById(1L);
        Assertions.assertNotEquals(Optional.empty(),ev_1);
        Assertions.assertEquals(1L, ev_1.get().getId());
    }
    @Test
    void should_not_find_event_by_id()  {
        Optional<Event> ev_122 = eventRepo.findById(122L);
        Assertions.assertEquals(Optional.empty(),ev_122);
    }

    @Test
    void should_add_event() {
        List<Event> me = (List<Event>) context.getBean("mockEvents");
        List<Event> startMe = new ArrayList<>(me);
        Event toAdd = Event.builder()
                .author("Sebastian")
                .title("Error in server room")
                .place("Server Room")
                .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                .dateTime(LocalDateTime.of(2022,12,2,12,23))
                .severity(Severity.ALARM)
                .build();
        Event newEv = eventRepo.save(toAdd);
        Assertions.assertEquals(toAdd.getAuthor(),newEv.getAuthor());
        Assertions.assertEquals(startMe.size() +1, me.size());
        Assertions.assertEquals(startMe.size() + 1, newEv.getId());
    }

    @Test
    @DisplayName("should update event")
    void zz_should_update_event() {
        List<Event> me = (List<Event>) context.getBean("mockEvents");
        List<Event> startMe = new ArrayList<>(me);
        Event oldEv = eventRepo.findById(1L).get();
        Event toUp = Event.builder()
                .id(1L)
                .author("Sebastian")
                .title("Error in server room")
                .place("Server Room")
                .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                .dateTime(LocalDateTime.of(2022,12,2,12,23))
                .severity(Severity.ALARM)
                .build();
        Event newEv = eventRepo.save(toUp);
        eventRepo.delete(oldEv);
        Assertions.assertNotEquals(oldEv.getAuthor(),newEv.getAuthor());
        Assertions.assertEquals(oldEv.getId(),newEv.getId());
        Assertions.assertEquals(startMe.size() , me.size());
    }

    @Test
    void should_add_all_events() {
        List<Event> me = (List<Event>) context.getBean("mockEvents");
        List<Event> startMe = new ArrayList<>(me);
        List<Event> toAdds = new ArrayList<>( List.of (
                    Event.builder()
                        .author("Sebastian")
                        .title("Error in server room")
                        .place("Server Room")
                        .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                        .dateTime(LocalDateTime.of(2022,12,2,12,23))
                        .severity(Severity.ALARM)
                        .build(),
                    Event.builder()
                        .author("Gaia")
                        .title("Error in server room")
                        .place("Server Room")
                        .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                        .dateTime(LocalDateTime.of(2022,12,2,12,23))
                        .severity(Severity.ALARM)
                        .build()
        ));
        eventRepo.saveAll(toAdds);
        Assertions.assertEquals(startMe.size() + 1,toAdds.get(0).getId());
        Assertions.assertEquals(startMe.size() + 2,toAdds.get(1).getId());
        Assertions.assertEquals(startMe.size() +2, me.size());
    }



    @Test
    @DisplayName("should delete event")
    void z_should_delete_event() {
        List<Event> me = (List<Event>) context.getBean("mockEvents");
        List<Event> startMe = new ArrayList<>(me);
        Event toDel = startMe.get(1);
        eventRepo.delete(toDel);
        Assertions.assertEquals(startMe.size() -1, me.size());
        for( Event e : me){
            Assertions.assertNotEquals(toDel.getId(), e.getId());
        }
    }
}

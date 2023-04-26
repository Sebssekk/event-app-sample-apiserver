package eventApp.apiServer.events;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EventRepoTest {
    @Autowired
    private EventRepository eventRepo;
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private ApplicationContext context;

    static private List<Event> me;

    @BeforeEach
    void setup(){

            me = List.of(
                    Event.builder()
                            //.id(1L)
                            .author("Ben")
                            .title("Error in server room")
                            .place("Server Room")
                            .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                            .dateTime(LocalDateTime.of(2022, 12, 2, 12, 23))
                            .severity(Severity.ALARM)
                            .build(),
                    Event.builder()
                            //.id(2L)
                            .author("John")
                            .title("Clean completed")
                            .place("2nd floor")
                            .description("Cleaning operation was successfully completed at second floor, now moving at 3rd")
                            .dateTime(LocalDateTime.of(2022, 10, 1, 9, 17))
                            .severity(Severity.SUCCESS)
                            .build(),
                    Event.builder()
                            //.id(3L)
                            .author("Susan")
                            .title("Migration completed")
                            .place("Datacenter 3")
                            .description("Vm migration was successfully completed from datacenter 3 to datacenter 4")
                            .dateTime(LocalDateTime.of(2022, 2, 5, 18, 5))
                            .severity(Severity.SUCCESS)
                            .build(),
                    Event.builder()
                            //.id(4L)
                            .author("Andrea")
                            .title("Package delivered")
                            .place("Entrance")
                            .description("A package was delivered at entrance. Someone should go there and check")
                            .dateTime(LocalDateTime.of(2022, 6, 29, 14, 16))
                            .severity(Severity.INFO)
                            .build(),
                    Event.builder()
                            //.id(5L)
                            .author("Luis")
                            .title("Running out of Storage resources")
                            .place("Datacenter 1")
                            .description("We are running out of resources in datacenter 1, we should consider to buy more storage resources")
                            .dateTime(LocalDateTime.of(2022, 8, 10, 19, 1))
                            .severity(Severity.WARNING)
                            .build()
            );
            //List<Event> me = (List<Event>) context.getBean("mockEvents");
            //me.forEach(e -> e.setId(null));
            me.forEach(e -> testEntityManager.persist(e));

    }
    @Test
    void should_find_all_starting_events() {
        List<Event> allEv = eventRepo.findAll();
        Assertions.assertEquals(allEv.size(), me.size());
    }

    @Test
    void should_find_event_by_id() {
        Long id = eventRepo.findAll().get(0).getId();
        Optional<Event> ev_1 = eventRepo.findById(id);
        Assertions.assertNotEquals(Optional.empty(),ev_1);
        Assertions.assertEquals(id, ev_1.get().getId());
    }
    @Test
    void should_not_find_event_by_id()  {
        Optional<Event> ev_122 = eventRepo.findById(122L);
        Assertions.assertEquals(Optional.empty(),ev_122);
    }

    @Test
    void should_add_event() {
        int startingSize = eventRepo.findAll().size();
        System.out.println(startingSize);

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
        Assertions.assertEquals(startingSize + 1, eventRepo.findAll().size());
    }

    @Test
    @DisplayName("should update event")
    void zz_should_update_event() {
        Long id = eventRepo.findAll().get(0).getId();
        int startingSize = eventRepo.findAll().size();
        Event ev = eventRepo.findById(id).get();
        String oldAuthor = ev.getAuthor();
        ev.setAuthor("UpdatedAuthor");

        Assertions.assertNotEquals(eventRepo.findById(id).get().getAuthor(),oldAuthor);
        Assertions.assertEquals(eventRepo.findById(id).get().getAuthor(),"UpdatedAuthor");
        Assertions.assertEquals(startingSize , eventRepo.findAll().size());
    }

    @Test
    void should_add_all_events() {
        int startingSize = eventRepo.findAll().size();
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
        Assertions.assertEquals(startingSize + toAdds.size(),eventRepo.findAll().size());
    }



    @Test
    @DisplayName("should delete event")
    void z_should_delete_event() {
        Long id = eventRepo.findAll().get(0).getId();
        int startingSize = eventRepo.findAll().size();
        Event toDel = eventRepo.findById(id).get();
        eventRepo.delete(toDel);
        Assertions.assertEquals(startingSize -1, eventRepo.findAll().size());
        Assertions.assertEquals(Optional.empty(),eventRepo.findById(id));

    }
}

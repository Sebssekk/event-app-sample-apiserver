package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EventServicetest {

    @MockBean
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;
    private List<Event> mockevs;

    @BeforeEach
    void setUp() throws EventNotFoundException {
        this.mockevs = new ArrayList<>(List.of(
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
                        .build()
        ));

        Mockito.when(eventRepository.findAll()).thenReturn(mockevs);
        Mockito.when(eventRepository.findById(1l)).thenReturn(Optional.of(mockevs.get(0)));
        Mockito.when(eventRepository.findById(3l)).thenReturn(Optional.empty());
        Mockito.when(eventRepository.saveAll(Mockito.anyList())).thenReturn(mockevs);
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(mockevs.get(0));
        Mockito.doNothing().when(eventRepository).delete(mockevs.get(0));
    }

    @Test
    void should_find_all_events(){
        List<Event> found = eventService.getEvents();
        int mockevsSize = mockevs.size();
        Assertions.assertTrue(found.size() == mockevsSize);
        Mockito.verify(eventRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(eventRepository);
    }


    @Test
    void should_find_and_return_one_event() throws EventNotFoundException {
        Event found = eventService.getEvent(1l);
        Assertions.assertEquals("Ben",found.getAuthor());
        Mockito.verify(eventRepository, Mockito.times(1)).findById(1l);
        Mockito.verifyNoMoreInteractions(eventRepository);
    }


    @Test
    void should_not_find_and_throw_exception() throws EventNotFoundException {
        Assertions.assertThrows(EventNotFoundException.class, () -> eventService.getEvent(3l));

        Mockito.verify(eventRepository, Mockito.times(1)).findById(3l);
        Mockito.verifyNoMoreInteractions(eventRepository);
    }
    @Test
    void should_save_all_given_events() {

        List<Event> toSave = List.of(
                Event.builder()
                        .author("Ben")
                        .title("Error in server room")
                        .place("Server Room")
                        .description("An error occurred in server room and everython g is burning now. I'm super scared about it, need help!")
                        .dateTime(LocalDateTime.of(2022,12,2,12,23))
                        .severity(Severity.ALARM)
                        .build(),
                Event.builder()
                        .author("John")
                        .title("Clean completed")
                        .place("2nd floor")
                        .description("Cleaning operation was successfully completed at second floor, now moving at 3rd")
                        .dateTime(LocalDateTime.of(2022,10,1,9,17))
                        .severity(Severity.SUCCESS)
                        .build()
        );
        List<Event> events = eventService.addEvents(toSave);
        Assertions.assertTrue(events.size() == toSave.size());

        Mockito.verify(eventRepository, Mockito.times(1)).saveAll(toSave);
        Mockito.verifyNoMoreInteractions(eventRepository);
    }
    @Test
    void should_save_given_event() {
        Event toSave = Event.builder()
                .author("Ben")
                .title("Error in server room")
                .place("Server Room")
                .description("An error occurred in server room and everython g is burning now. I'm super scared about it, need help!")
                .dateTime(LocalDateTime.of(2022,12,2,12,23))
                .severity(Severity.ALARM)
                .build();
        Event ev = eventService.addEvent(toSave);
        Assertions.assertEquals(1l,ev.getId());

        Mockito.verify(eventRepository, Mockito.times(1)).save(toSave);
        Mockito.verifyNoMoreInteractions(eventRepository);
    }
    @Test
    void should_delete_event() throws EventNotFoundException {
        Event toDelete = mockevs.get(0);
        eventService.deleteEvent(toDelete.getId());

        Mockito.verify(eventRepository, Mockito.times(1)).findById(toDelete.getId());
        Mockito.verify(eventRepository, Mockito.times(1)).delete(toDelete);
        Mockito.verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void should_not_delete_event_that_does_not_exist() throws EventNotFoundException {
        Assertions.assertThrows(EventNotFoundException.class, () -> eventService.deleteEvent(3l));

        Mockito.verify(eventRepository, Mockito.times(1)).findById(3l);
        Mockito.verify(eventRepository, Mockito.times(0)).delete(Mockito.any(Event.class));
        Mockito.verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void should_update_an_event() throws EventNotFoundException {
        Event updatedEv = eventService.updateEvent(1l,Event.builder()
                .author("Susan")
                .title("Error in server room Again")
                .place("Server Room")
                .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                .dateTime(LocalDateTime.of(2022,12,2,15,23))
                .severity(Severity.ALARM)
                .build());
        Assertions.assertEquals(1l,updatedEv.getId());
        Assertions.assertEquals("Susan",updatedEv.getAuthor());
    }
    @Test
    void should_not_update_event_that_does_not_exist() throws EventNotFoundException {
        Event toUpdateEv = Event.builder()
                .author("Susan")
                .title("Error in server room Again")
                .place("Server Room")
                .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                .dateTime(LocalDateTime.of(2022,12,2,15,23))
                .severity(Severity.ALARM)
                .build();
        Assertions.assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(3l, toUpdateEv));
    }
}

package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EventServicetest {

    @MockBean
    private EventRepository eventRepository;
    @Autowired
    private EventService eventService;


    @BeforeEach
    void setUp() throws EventNotFoundException {
        List<Event> evs = new ArrayList<>(List.of(
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
        Mockito.when(eventRepository.findAll()).thenReturn(evs);
        Mockito.when(eventRepository.getEventById(1l)).thenReturn(Optional.of(evs.get(0)));
        Mockito.when(eventRepository.saveAll(evs)).thenReturn(evs);
        Mockito.when(eventRepository.save(evs.get(0))).thenReturn(evs.get(0));
        Mockito.when(eventRepository.deleteEventById(Mockito.anyLong())).thenReturn(Optional.of(evs.get(0)));

    }

    @Test
    void should_find_all_events(){
        List<Event> found = eventService.getEvents();
        Assertions.assertTrue(found.size() == 2);
        Mockito.verify(eventRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(eventRepository);
    }


    @Test
    void should_find_and_return_one_event() throws EventNotFoundException {
        Event found = eventService.getEvent(1l);
        Assertions.assertEquals("Ben",found.getAuthor());
        Mockito.verify(eventRepository, Mockito.times(1)).getEventById(1l);
        Mockito.verifyNoMoreInteractions(eventRepository);
    }


    @Test
    void should_not_find_and_throw_exception() throws EventNotFoundException {
       Mockito.when(eventRepository.getEventById(3l)).thenReturn(Optional.empty());

        Assertions.assertThrows(EventNotFoundException.class, () -> eventService.getEvent(3l));

        Mockito.verify(eventRepository, Mockito.times(1)).getEventById(3l);
        Mockito.verifyNoMoreInteractions(eventRepository);
    }
}

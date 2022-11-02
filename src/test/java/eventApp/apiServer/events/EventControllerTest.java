package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventService eventService;

    private List<Event> mockEvs;

    @BeforeEach
    public void setUp(){
        this.mockEvs = new ArrayList<>(List.of(
                Event.builder()
                        .id(1L)
                        .author("Ben")
                        .title("Error in server room")
                        .place("Server Room")
                        .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
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
    }
    @Test
    public void should_get_all_events() throws Exception {
        Mockito.when(eventService.getEvents()).thenReturn(mockEvs);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L));

    }

    @Test
    public void should_get_one_event() throws Exception {
        Mockito.when(eventService.getEvent(1L)).thenReturn(mockEvs.get(0));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Ben"));
    }

    @Test
    public void should_not_find_one_event() throws Exception {
        Mockito.when(eventService.getEvent(3L)).thenThrow(EventNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/events/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("NOT_FOUND"));
    }
    @Test
    public void should_add_an_event() throws Exception {
        Mockito.when(eventService.addEvent(Mockito.any(Event.class))).thenReturn(mockEvs.get(0));

        String json = """
                {
                  "title": "Error in server roo",
                  "description": "An error occurred in server room and everython g is burning now. I'm super scared about it, need help!",
                  "dateTime": "2022-12-02T12:23:00",
                  "place": "Server Room",
                  "severity":{"value":"ALARM"},
                  "author": "Ben"
                }""";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/events/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }
    @Test
    public void should_add_all_events() throws Exception {
        Mockito.when(eventService.addEvents(Mockito.anyList())).thenReturn(mockEvs);

        String json = """
                [{
                  "title": "Error in server room",
                  "description": "An error occurred in server room and everython g is burning now. I'm super scared about it, need help!",
                  "dateTime": "2022-12-02T12:23:00",
                  "place": "Server Room",
                  "severity": {"value":"ALARM"},
                  "author": "Ben"
                },
                {
                  "title": "Clean completed",
                  "description": "Cleaning operation was successfully completed at second floor, now moving at 3rd",
                  "dateTime": "2022-10-01T09:17:00",
                  "place": "2nd floor",
                  "severity": {"value": "SUCCESS"},
                  "author": "John"
                }]""";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/events/batchadd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    public void should_delete_event() throws Exception {
        Mockito.when(eventService.deleteEvent(1L)).thenReturn(mockEvs.get(0));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Ben"));
    }
    @Test
    public void should_not_delete_event_that_does_not_exist() throws Exception {
        Mockito.when(eventService.deleteEvent(3L)).thenThrow(EventNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/events/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNotFound());
    }

    @Test
    void should_update_an_event() throws Exception {
        Event updatedEvent = Event.builder()
                .id(1L)
                .author("Susan")
                .title("Error in server room Again")
                .place("Server Room")
                .description("An error occurred in server room and everything is burning now. I'm super scared about it, need help!")
                .dateTime(LocalDateTime.of(2022,12,2,15,23))
                .severity(Severity.ALARM)
                .build();

        Mockito.when(eventService.updateEvent(Mockito.eq(1L),Mockito.any(Event.class))).thenReturn(updatedEvent);

        String json = """
                {
                  "title": "Error in server room Again",
                  "description": "An error occurred in server room and everything is burning now. I'm super scared about it, need help!",
                  "dateTime": "2022-12-02T15:23:00",
                  "place": "Server Room",
                  "severity":{"value":"ALARM"},
                  "author": "Susan"
                }""";
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }
    @Test
    public void should_not_find_event_to_update() throws Exception {
        Mockito.when(eventService.updateEvent(Mockito.eq(3L),Mockito.any(Event.class))).thenThrow(EventNotFoundException.class);

        String json = """
                {
                  "title": "Error in server room Again",
                  "description": "An error occurred in server room and everything is burning now. I'm super scared about it, need help!",
                  "dateTime": "2022-12-02T15:23:00",
                  "place": "Server Room",
                  "severity":{"value":"ALARM"},
                  "author": "Susan"
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/events/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isNotFound());
    }
}

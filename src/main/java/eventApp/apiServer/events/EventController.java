package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api/events")
@AllArgsConstructor
public class EventController {
    @Autowired
    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getEvents(){
         List<Event> evs = eventService.getEvents();
         return ResponseEntity.ok(evs);
    }
    @GetMapping(path = "{id}")
    public ResponseEntity<Event> getEvent(@PathVariable("id") Long id) throws EventNotFoundException {
        Event ev = eventService.getEvent(id);
        return ResponseEntity.ok(ev);
    }

    @PostMapping(path = "{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") Long id, @RequestBody Event ev) throws EventNotFoundException {
        Event updated_ev = eventService.updateEvent(id,ev);
        return ResponseEntity.created(URI.create(id.toString())).body(updated_ev);
    }

    @PostMapping(path="add")
    public ResponseEntity<Event> addEvent(@RequestBody Event event){
        Event ev = eventService.addEvent(event);
        return ResponseEntity.created(URI.create(ev.getId().toString())).body(ev);
    }

    @PostMapping(path = "batchadd")
    public  ResponseEntity<List<Event>> addEvents(@RequestBody List<Event> events){
        List<Event> evs = eventService.addEvents(events);
        return ResponseEntity.created(URI.create("/")).body(evs);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) throws EventNotFoundException {
        Event ev = eventService.deleteEvent(id);
        return ResponseEntity.ok().body(ev);
    }

}

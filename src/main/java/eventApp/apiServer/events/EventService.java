package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    @Autowired
    private final EventRepository eventRepository;
    public List<Event> getEvents() {
        return eventRepository.findAll();
    }
    public Event getEvent(Long id) throws EventNotFoundException {
        return eventRepository.getEventById(id).orElseThrow(()->new EventNotFoundException());
    }

    public Event addEvent(Event ev){
        return eventRepository.save(ev);
    }
    public List<Event> addEvents(List<Event> evs){
        return eventRepository.saveAll(evs);
    }
    public Event deleteEvent(Long id) throws EventNotFoundException {
        return eventRepository.deleteEventById(id).orElseThrow(() -> new EventNotFoundException());
    }


}

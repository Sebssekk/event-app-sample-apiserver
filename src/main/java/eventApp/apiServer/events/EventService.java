package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
    }

    public Event addEvent(Event ev){
        return eventRepository.save(ev);
    }
    public List<Event> addEvents(List<Event> evs){
        return eventRepository.saveAll(evs);
    }
    public Event deleteEvent(Long id) throws EventNotFoundException {
        Event ev = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        eventRepository.delete(ev);
        return ev;
    }
    //@Transactional // Only with real DB
    public Event updateEvent(Long id, Event ev) throws EventNotFoundException {
        Event oldEv = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        oldEv.setPlace(ev.getPlace());
        oldEv.setAuthor(ev.getAuthor());
        oldEv.setSeverity(ev.getSeverity());
        oldEv.setTitle(ev.getTitle());
        oldEv.setDescription(ev.getDescription());
        oldEv.setDateTime(ev.getDateTime());

        // - Only for mock DB, DELETE with real DB
        Event oldOldEv = eventRepository.findById(id).orElseThrow(EventNotFoundException::new);
        eventRepository.delete(oldOldEv);
        oldOldEv.setId(id);
        eventRepository.save(oldEv);

        return oldEv;
    }


}

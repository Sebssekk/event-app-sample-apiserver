package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class MockEventRepo implements EventRepository{
    @Autowired
    private List<Event> events;
    @Override
    public List<Event> findAll() {
        return events;
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return  events.stream().filter(ev -> ev.getId() == id).findFirst();

    }

    @Override
    public Event save(Event event) {
        events.add(event);
        return event;
    }

    @Override
    public List<Event> saveAll(List<Event> events) {
        events.addAll(events);
        return events;
    }

    @Override
    public Optional<Event> deleteEventById(Long id) {
        Optional<Event> event = this.getEventById(id);
        events.remove(event.get());
        return event;
    }
}

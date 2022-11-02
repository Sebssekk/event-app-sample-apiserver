package eventApp.apiServer.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MockEventRepo implements EventRepository{
    private long id_Sequence;
    @Autowired
    private final List<Event> dbEvents;

    public MockEventRepo(List<Event> events) {
        this.id_Sequence = (long) events.size()+1;
        this.dbEvents = events;
    }

    @Override
    public List<Event> findAll() {
        return dbEvents;
    }

    @Override
    public Optional<Event> findById(Long id) {
        return  dbEvents.stream().filter(ev -> ev.getId().equals(id)).findFirst();

    }

    @Override
    public Event save(Event event) {
        if (event.getId() == null) {
            event.setId(id_Sequence);
            id_Sequence++;
        }
        dbEvents.add(event);
        return event;
    }

    @Override
    public List<Event> saveAll(List<Event> events) {
        for (Event ev : events){
            if (ev.getId() == null) {
                ev.setId(id_Sequence);
                id_Sequence++;
            }
        }
        dbEvents.addAll(events);
        return events;
    }

    @Override
    public void  delete(Event event) {
        dbEvents.remove(event);
    }
}

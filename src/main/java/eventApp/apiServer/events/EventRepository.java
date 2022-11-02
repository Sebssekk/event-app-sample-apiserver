package eventApp.apiServer.events;

import eventApp.apiServer.events.errors.EventNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository {
    public List<Event> findAll();
    public Optional<Event> findById(Long id) throws EventNotFoundException;
    public Event save(Event event);
    public List<Event> saveAll(List<Event> events);
    public void delete( Event event) throws EventNotFoundException;
}

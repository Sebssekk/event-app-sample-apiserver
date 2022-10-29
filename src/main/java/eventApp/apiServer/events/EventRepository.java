package eventApp.apiServer.events;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository {
    public List<Event> findAll();
    public Event getEventById(Long id);
    public void save(Event event);
    public void saveAll(List<Event> events);
    public void deleteEventById(Long id);
}

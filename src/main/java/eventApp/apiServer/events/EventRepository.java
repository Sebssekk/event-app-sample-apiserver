package eventApp.apiServer.events;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository {
    List<Event> findAll();
    Optional<Event> findById(Long id);
    Event save(Event event);
    List<Event> saveAll(List<Event> events);
    void delete( Event event);
}

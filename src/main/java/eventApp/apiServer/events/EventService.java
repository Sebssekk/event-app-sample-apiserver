package eventApp.apiServer.events;

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
}

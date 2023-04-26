package eventApp.apiServer.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository  extends  JpaRepository<Event,Long>{

    // ** All the following method are already defined in JPARepository ** //

    //    List<Event> findAll();
    //    Optional<Event> findById(Long id);
    //    Event save(Event event);
    //    List<Event> saveAll(List<Event> events);
    //    void delete( Event event);
}

package lab.ieadi.msagenda.repositories;

import lab.ieadi.msagenda.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}

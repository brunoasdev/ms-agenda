package lab.ieadi.msagenda.services;

import lab.ieadi.msagenda.model.Event;
import lab.ieadi.msagenda.model.EventRequestDTO;
import lab.ieadi.msagenda.model.EventResponseDTO;
import lab.ieadi.msagenda.repositories.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private EventRepository repository = null;
    private LocalDate currentDate = null;

    public EventService(EventRepository eventRepository) {
        this.currentDate = LocalDate.now();
        this.repository = eventRepository;
    }

    @Transactional
    public EventResponseDTO insert(final EventRequestDTO requestEventDTO){
        return new EventResponseDTO(repository.save(Event.builder()
                                                                .title(requestEventDTO.getTitle())
                                                                .local(requestEventDTO.getLocal())
                                                                .start(requestEventDTO.getStart())
                                                                .build()));
    }

    public EventResponseDTO findById(final long id) {
        if(id > 0)
            return new EventResponseDTO(repository.findById(id).get());
        return null;
    }

    public List<EventResponseDTO> getAll(){
        return repository
                    .findAll()
                            .stream()
                            .sorted((o1, o2) -> o1.getStart().compareTo(o2.getStart()))
                            .map(EventResponseDTO::new)
                            .toList();
    }

    public List<EventResponseDTO> weekly() {
        var tomorrow = LocalDateTime.of(this.currentDate, LocalTime.MIDNIGHT).plusDays(1);
        var lastDay = tomorrow.plusDays(8).with(LocalTime.MAX);

        return repository.findAll()
                                .stream()
                                .filter(e -> e.getStart().isAfter(tomorrow))
                                .filter(e -> e.getStart().isBefore(lastDay))
                                .sorted((o1, o2) -> o1.getStart().compareTo(o2.getStart()))
                                .map(EventResponseDTO::new)
                                .toList();
    }

    public List<EventResponseDTO> monthly() {
        var firstDay = LocalDateTime.of(this.currentDate.with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
        var lastDay = LocalDateTime.of(this.currentDate.with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);

        return repository.findAll()
                                .stream()
                                .filter(e -> e.getStart().isAfter(firstDay))
                                .filter(e -> e.getStart().isBefore(lastDay))
                                .sorted((o1, o2) -> o1.getStart().compareTo(o2.getStart()))
                                .map(EventResponseDTO::new)
                                .toList();
    }

    public List<EventResponseDTO> yearly() {
        var firstDay = LocalDateTime.of(this.currentDate.with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIDNIGHT);
        var lastDay = LocalDateTime.of(this.currentDate.with(TemporalAdjusters.lastDayOfYear()), LocalTime.MAX);

        return repository.findAll()
                                .stream()
                                .filter(e -> e.getStart().isAfter(firstDay))
                                .filter(e -> e.getStart().isBefore(lastDay))
                                .sorted((o1, o2) -> o1.getStart().compareTo(o2.getStart()))
                                .map(EventResponseDTO::new)
                                .toList();
    }

    @Transactional
    public EventResponseDTO update(long id, EventRequestDTO dto) {
        var eventFound = findOptEventById(id);

        if(eventFound != null){
            eventFound.setTitle(dto.getTitle());
            eventFound.setLocal(dto.getLocal());
            eventFound.setStart(dto.getStart());
            return new EventResponseDTO(repository.save(eventFound));
        }

        return null;
    }

    @Transactional
    public void delete(long id) {
        var eventFound = findOptEventById(id);

        if(eventFound != null) {
            repository.deleteById(eventFound.getId());
        }
    }

    private Event findOptEventById(long id) {
        Optional<Event> eventFound = null;
        if(id > 0) {
            eventFound = repository.findById(id);
            if (eventFound.isEmpty()) return null;
        }
        return eventFound.get();
    }
}

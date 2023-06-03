package lab.ieadi.msagenda.model;

import java.time.LocalDateTime;

public record EventResponseDTO(Long id, String title, String local, LocalDateTime start) {
    public EventResponseDTO(Event event){
        this(event.getId(), event.getTitle(), event.getLocal(), event.getStart());
    }
}
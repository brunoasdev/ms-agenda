package lab.ieadi.msagenda.api;

import jakarta.validation.Valid;
import lab.ieadi.msagenda.model.EventRequestDTO;
import lab.ieadi.msagenda.model.EventResponseDTO;
import lab.ieadi.msagenda.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/events")
public class EventController {

    private EventService service = null;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EventResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponseDTO> findById(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/weekly", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventResponseDTO>> weekly(){
        return new ResponseEntity<>(service.weekly(), HttpStatus.OK);
    }

    @GetMapping(value = "/monthly", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventResponseDTO>> monthly(){
        return new ResponseEntity<>(service.monthly(), HttpStatus.OK);
    }

    @GetMapping(value = "/yearly", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventResponseDTO>> yearly(){
        return new ResponseEntity<>(service.yearly(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponseDTO> insert(@RequestBody final EventRequestDTO requestEventDTO) {
        var eventSaved = service.insert(requestEventDTO);
        return new ResponseEntity<>(eventSaved, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResponseDTO> update(@PathVariable("id") final Long id, @Valid @RequestBody final EventRequestDTO requestEventDTO){
        var eventUpdated = service.update(id, requestEventDTO);
        return new ResponseEntity<>(eventUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id){
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

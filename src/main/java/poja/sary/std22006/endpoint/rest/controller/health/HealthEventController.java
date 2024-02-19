package poja.sary.std22006.endpoint.rest.controller.health;

import static java.util.UUID.randomUUID;
import static poja.sary.std22006.endpoint.rest.controller.health.PingController.KO;
import static poja.sary.std22006.endpoint.rest.controller.health.PingController.OK;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import poja.sary.std22006.PojaGenerated;
import poja.sary.std22006.endpoint.event.EventProducer;
import poja.sary.std22006.endpoint.event.gen.UuidCreated;
import poja.sary.std22006.repository.DummyUuidRepository;

@PojaGenerated
@RestController
@AllArgsConstructor
public class HealthEventController {

  DummyUuidRepository dummyUuidRepository;
  EventProducer eventProducer;

  @GetMapping(value = "/health/event")
  public ResponseEntity<String> random_uuid_is_fired_then_created() throws InterruptedException {
    var randomUuid = randomUUID().toString();
    var event = new UuidCreated().toBuilder().uuid(randomUuid).build();

    eventProducer.accept(List.of(event));

    Thread.sleep(20_000);
    return dummyUuidRepository.findById(randomUuid).map(dummyUuid -> OK).orElse(KO);
  }
}

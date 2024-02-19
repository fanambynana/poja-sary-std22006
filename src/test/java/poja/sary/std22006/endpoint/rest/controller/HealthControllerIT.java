package poja.sary.std22006.endpoint.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static poja.sary.std22006.endpoint.rest.controller.health.PingController.OK;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import poja.sary.std22006.PojaGenerated;
import poja.sary.std22006.conf.FacadeIT;
import poja.sary.std22006.endpoint.rest.controller.health.HealthDbController;
import poja.sary.std22006.endpoint.rest.controller.health.PingController;

@PojaGenerated
class HealthControllerIT extends FacadeIT {

  @Autowired PingController pingController;
  @Autowired HealthDbController healthDbController;

  @Test
  void ping() {
    assertEquals("pong", pingController.ping());
  }

  @Test
  void can_read_from_dummy_table() {
    var responseEntity = healthDbController.dummyTable_should_not_be_empty();
    assertEquals(OK, responseEntity);
  }
}

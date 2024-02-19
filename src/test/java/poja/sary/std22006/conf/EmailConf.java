package poja.sary.std22006.conf;

import org.springframework.test.context.DynamicPropertyRegistry;
import poja.sary.std22006.PojaGenerated;

@PojaGenerated
public class EmailConf {

  void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("aws.ses.source", () -> "dummy-ses-source");
  }
}

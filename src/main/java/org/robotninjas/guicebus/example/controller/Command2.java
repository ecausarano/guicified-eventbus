package org.robotninjas.guicebus.example.controller;

import org.robotninjas.guicebus.Subscribe;
import org.robotninjas.guicebus.example.event.Event2;

import javax.inject.Inject;
import java.util.logging.Logger;

public class Command2 {

  @Inject Logger logger;

  @Subscribe public void handleEvent(Event2 event2) {
    logger.info("handling event2 " + event2.getMsg());
  }

}

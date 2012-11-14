package org.robotninjas.guicebus.example.controller;

import org.robotninjas.guicebus.Command;
import org.robotninjas.guicebus.Subscribe;
import org.robotninjas.guicebus.example.event.Event1;
import org.robotninjas.guicebus.example.service.DatabaseService;

import javax.inject.Inject;
import java.util.logging.Logger;

public class Command1 implements Command {

  @Inject Logger logger;
  @Inject DatabaseService dbService;

  @Subscribe public void handleEvent(Event1 e) {
    logger.info("" + e.getMsg());
    dbService.doStuff();
  }

}

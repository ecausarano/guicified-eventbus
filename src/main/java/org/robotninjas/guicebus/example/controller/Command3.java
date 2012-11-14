package org.robotninjas.guicebus.example.controller;

import org.robotninjas.guicebus.Command;
import org.robotninjas.guicebus.Subscribe;
import org.robotninjas.guicebus.example.event.Event1;

import javax.inject.Inject;
import java.util.logging.Logger;

public class Command3 implements Command{

  @Inject Logger logger;

  @Subscribe public void handleEvent(Event1 e) {
    logger.info("Just logging " + e.getMsg());
  }

}

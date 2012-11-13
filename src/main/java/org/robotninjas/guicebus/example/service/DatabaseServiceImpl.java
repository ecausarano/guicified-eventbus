package org.robotninjas.guicebus.example.service;


import com.google.common.util.concurrent.AbstractService;

import javax.inject.Inject;
import java.util.logging.Logger;

public class DatabaseServiceImpl extends AbstractService implements DatabaseService {

  @Inject Logger logger;

  @Override protected void doStart() {
    logger.info("starting the db service");
    notifyStarted();
  }

  @Override protected void doStop() {
    logger.info("stopping the db service");
    notifyStopped();
  }

  @Override public void doStuff() {
    logger.info("doing stuff to db");
  }
}

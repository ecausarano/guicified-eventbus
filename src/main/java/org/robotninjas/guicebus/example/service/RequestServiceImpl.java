package org.robotninjas.guicebus.example.service;

import com.google.common.util.concurrent.AbstractService;
import org.robotninjas.guicebus.CommandBus;
import org.robotninjas.guicebus.example.event.Event1;
import org.robotninjas.guicebus.example.event.Event2;

import javax.inject.Inject;
import java.util.logging.Logger;

public class RequestServiceImpl extends AbstractService implements RequestService {

  @Inject Logger logger;
  @Inject CommandBus eventBusImpl;

  @Override protected void doStart() {
    logger.info("starting request service");
    notifyStarted();
  }

  @Override protected void doStop() {
    logger.info("stopping request service");
    notifyStopped();
  }

  @Override public void processRequest(int num) {
    logger.info("processing request");
    eventBusImpl.post(new Event1(num));
  }

  @Override public void processMessage(String msg) {
    logger.info("processing message");
    eventBusImpl.post(new Event2(msg));
  }
}

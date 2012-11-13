package org.robotninjas.guicebus.example;

import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.robotninjas.guicebus.*;
import org.robotninjas.guicebus.example.controller.Command1;
import org.robotninjas.guicebus.example.controller.Command2;
import org.robotninjas.guicebus.example.event.Event1;
import org.robotninjas.guicebus.example.event.Event2;
import org.robotninjas.guicebus.example.service.DatabaseService;
import org.robotninjas.guicebus.example.service.DatabaseServiceImpl;
import org.robotninjas.guicebus.example.service.RequestService;
import org.robotninjas.guicebus.example.service.RequestServiceImpl;

public class TestApplication extends AbstractModule {

  @Override protected void configure() {
    bind(DatabaseService.class).to(DatabaseServiceImpl.class);
    bind(RequestService.class).to(RequestServiceImpl.class);
  }

  public static void main(String[] args) {

    final Injector injector = Guice.createInjector(
        new TestApplication(),
        new CommandModule(new CommandRegistration() {
          @Override public void configure(Multimap<Class<? extends Command>, Class<? extends Event>> eventMap) {
            eventMap.put(Command1.class, Event1.class);
            eventMap.put(Command2.class, Event2.class);
          }
        }));

    final Service dbService = injector.getInstance(DatabaseServiceImpl.class);
    final RequestServiceImpl reqService = injector.getInstance(RequestServiceImpl.class);

    dbService.startAndWait();
    reqService.startAndWait();

    Runtime.getRuntime().addShutdownHook(
        new Thread(
            new Runnable() {
              @Override public void run() {
                reqService.stopAndWait();
                dbService.stopAndWait();
              }
            }));

    for (int i = 0; i < 1000; i++) {
      reqService.processRequest(i);
      reqService.processMessage("hi");
    }

  }

}

package org.robotninjas.guicebus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class CommandModule extends AbstractModule {

  private final CommandRegistration registration;

  public CommandModule(CommandRegistration registration) {
    this.registration = registration;
  }

  @Override protected void configure() {

  }

  protected @Provides @Singleton GuiceInjectedEventBus createEventBus(Injector injector) {
    final GuiceInjectedEventBus eventBus = new GuiceInjectedEventBus(injector);
    final Multimap<Class<? extends Command>, Class<? extends Event>> eventMap = HashMultimap.create();
    registration.configure(eventMap);
    eventBus.register(eventMap);
    return eventBus;
  }

}

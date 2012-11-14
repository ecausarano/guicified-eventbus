package org.robotninjas.guicebus;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Provider;

class CommandBusProvider implements Provider<CommandBus> {

  private final Injector injector;
  private final CommandContext context;

  @Inject public CommandBusProvider(Injector injector, CommandContext context) {
    this.injector = injector;
    this.context = context;
  }

  @Override public CommandBus get() {
    final CommandBusImpl eventBusImpl = new CommandBusImpl(injector);
    final Multimap<Class<? extends Command>, Class<? extends Event>> eventMap = HashMultimap.create();
    context.configure(eventMap);
    eventBusImpl.register(eventMap);
    return eventBusImpl;
  }
}

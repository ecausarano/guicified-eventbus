package org.robotninjas.guicebus;

import com.google.inject.AbstractModule;

public class CommandModule extends AbstractModule {

  private final CommandContext context;

  public CommandModule(CommandContext context) {
    this.context = context;
  }

  @Override protected void configure() {
    bind(CommandContext.class).toInstance(context);
    bind(CommandBus.class).toProvider(CommandBusProvider.class).asEagerSingleton();
  }

}

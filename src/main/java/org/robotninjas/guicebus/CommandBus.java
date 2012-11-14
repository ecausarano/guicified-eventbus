package org.robotninjas.guicebus;

public interface CommandBus {
  void post(Event event);
}

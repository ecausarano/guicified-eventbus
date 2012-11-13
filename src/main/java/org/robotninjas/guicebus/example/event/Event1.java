package org.robotninjas.guicebus.example.event;


import org.robotninjas.guicebus.Event;

public class Event1 implements Event {
  private final int msg;

  public Event1(int msg) {
    this.msg = msg;
  }

  public int getMsg() {
    return msg;
  }
}

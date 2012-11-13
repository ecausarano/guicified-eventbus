package org.robotninjas.guicebus.example.event;


public class Event {
  private final int msg;

  public Event(int msg) {
    this.msg = msg;
  }

  public int getMsg() {
    return msg;
  }
}

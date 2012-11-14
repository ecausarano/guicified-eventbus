package org.robotninjas.guicebus.example.event;

import org.robotninjas.guicebus.Event;

public class Event2 implements Event {

  private final String msg;

  public Event2(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

}

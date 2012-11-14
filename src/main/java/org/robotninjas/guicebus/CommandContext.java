package org.robotninjas.guicebus;

import com.google.common.collect.Multimap;

public interface CommandContext {

  void configure(Multimap<Class<? extends Command>, Class<? extends Event>> eventMap);

}

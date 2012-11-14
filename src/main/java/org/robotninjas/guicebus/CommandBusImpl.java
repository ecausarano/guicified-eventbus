package org.robotninjas.guicebus;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import static com.google.common.base.Throwables.propagate;

class CommandBusImpl implements CommandBus {

  private final Injector injector;
  private final Multimap<Class<? extends Event>, Method> eventHandlerMap;

  @Inject public CommandBusImpl(Injector injector) {
    this.injector = injector;
    this.eventHandlerMap = ArrayListMultimap.create();
  }

  synchronized void register(Class<? extends Command> handler, Class<? extends Event> event) {

    boolean found = false;
    for (final Method method : handler.getMethods()) {
      System.out.println(method);
      if (method.getAnnotation(Subscribe.class) != null) {

        final Class<?>[] parameters = method.getParameterTypes();
        if (parameters.length != 1) {
          throw new RuntimeException("Illegal @Subsribe method, it must take one and only argument");
        }

        final Class<?> parameter = parameters[0];
        if (parameter == event) {
          eventHandlerMap.put(event, method);
          found = true;
        }

      }
    }

    if (!found) {
      throw new RuntimeException("Could not find handler method for  " + event);
    }

  }

  synchronized void register(Multimap<Class<? extends Command>, Class<? extends Event>> eventMap) {
    for (final Map.Entry<Class<? extends Command>, Class<? extends Event>> entry : eventMap.entries()) {
      register(entry.getKey(), entry.getValue());
    }
  }

  synchronized void unregister(Class<? extends Command> handler, Class<? extends Event> event) {

    final Iterator<Method> methodItr = eventHandlerMap.get(event.getClass().cast(Event.class)).iterator();
    while (methodItr.hasNext()) {
      final Method method = methodItr.next();
      if (method.getDeclaringClass().equals(handler)) {
        methodItr.remove();
        return;
      }
    }

  }

  @Override public void post(Event event) {

    final ImmutableList<Method> methods;
    synchronized (this) {
      methods = ImmutableList.copyOf(eventHandlerMap.get(event.getClass()));
    }

    for (final Method handlerMethod : methods) {
      try {
        final Object handler = injector.getInstance(handlerMethod.getDeclaringClass());
        handlerMethod.invoke(handler, event);
      } catch (Exception e) {
        throw propagate(e);
      }
    }

  }

}

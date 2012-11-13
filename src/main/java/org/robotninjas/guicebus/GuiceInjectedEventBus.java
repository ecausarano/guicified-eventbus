package org.robotninjas.guicebus;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Injector;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class GuiceInjectedEventBus {

  private final Injector injector;
  private final Multimap<Class<?>, Method> eventHandlerMap;

  @Inject public GuiceInjectedEventBus(Injector injector) {
    this.injector = injector;
    this.eventHandlerMap = ArrayListMultimap.create();
  }

  public void register(Class<?> handler, Class<?> event) {

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

  public void unregister(Class<?> handler, Class<?> event) {

    final Iterator<Method> methodItr = eventHandlerMap.get(event.getClass()).iterator();
    while (methodItr.hasNext()) {
      final Method method = methodItr.next();
      if (method.getDeclaringClass().equals(handler)) {
        methodItr.remove();
        return;
      }
    }

  }

  public void post(Object event) {
    for (final Method handlerMethod : eventHandlerMap.get(event.getClass())) {
      final Object handler = injector.getInstance(handlerMethod.getDeclaringClass());
      try {
        handlerMethod.invoke(handler, event);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

}

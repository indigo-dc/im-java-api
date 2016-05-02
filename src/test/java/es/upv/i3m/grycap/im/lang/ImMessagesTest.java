package es.upv.i3m.grycap.im.lang;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ImMessagesTest {

  /**
   * Try to build a private class.
   */
  @Test(expected = InvocationTargetException.class)
  public void testImMessagesBuilder() throws NoSuchMethodException,
      SecurityException, InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException {

    Constructor<ImMessages> constructor =
        ImMessages.class.getDeclaredConstructor(new Class[0]);
    constructor.setAccessible(true);
    constructor.newInstance(new Object[0]);
  }

  @Test
  public void testMessages() {
    Assert.assertEquals("REST call with empty body content",
        ImMessages.INFO_EMPTY_BODY_CONTENT);
  }
}

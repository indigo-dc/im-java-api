package es.upv.i3m.grycap.im.lang;

import org.junit.Assert;
import org.junit.Test;

public class ImMessagesTest {

  @Test
  public void testMessages() {
    Assert.assertEquals("REST call with empty body content",
        ImMessages.INFO_EMPTY_BODY_CONTENT);
  }
}

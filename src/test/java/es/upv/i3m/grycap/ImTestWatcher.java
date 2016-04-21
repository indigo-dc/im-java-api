package es.upv.i3m.grycap;

import es.upv.i3m.grycap.logger.ImJavaApiLogger;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ImTestWatcher {

  @Rule
  public TestRule watcher = new TestWatcher() {
    protected void starting(Description description) {
      ImJavaApiLogger.debug(ImTestWatcher.class,
          "Starting test: " + description.getMethodName());
    }
  };
}

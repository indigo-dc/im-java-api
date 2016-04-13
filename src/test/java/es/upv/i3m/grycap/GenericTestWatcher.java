package es.upv.i3m.grycap;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class GenericTestWatcher {

  @Rule
  public TestRule watcher = new TestWatcher() {
    protected void starting(Description description) {
      System.out.println("Starting test: " + description.getMethodName());
    }
  };
}

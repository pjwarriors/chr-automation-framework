package test.com.java.automation.base;

import com.google.common.annotations.VisibleForTesting;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Test;

public class DummyTest {
@Test
@Given("I run a dummy test")
public void i_run_a_dummy_test() {
    System.out.println("Running dummy Cucumber step");
}

    @Then("it should pass")
    public void it_should_pass() {
        System.out.println("Dummy Cucumber test passed");
    }
}

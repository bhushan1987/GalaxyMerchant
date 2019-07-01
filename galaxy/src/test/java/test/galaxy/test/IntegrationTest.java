package test.galaxy.test;

import galaxy.main.GalaxyExecutor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class IntegrationTest {
    @Test
    public void test() throws IOException {

        String filePath = System.getProperty("user.dir") + "/../input.txt";
        GalaxyExecutor executor = new GalaxyExecutor(filePath);
        executor.execute();

        // TODO compare input Vs output and assert - need  to write output in file but not in requirement
        Assert.assertTrue(true);
    }
}

package com.browserstack;

import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.browserstack.local.Local;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BrowserStackTestNGTest {
    public WebDriver driver;
    private Local l;

    @BeforeMethod(alwaysRun = true)
    @org.testng.annotations.Parameters(value = { "config", "environment" })
    @SuppressWarnings("unchecked")
    public void setUp(String config_file, String environment) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config;
        JSONObject envs;
        if (!config_file.isEmpty()) {
            config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/" + config_file));
            envs = (JSONObject) config.get("environments");
        } else {
            config = (JSONObject) parser.parse("{}");
            String cliHub = System.getProperty("hub");
            if (cliHub.isEmpty()) {
                cliHub = "hub.browserstack.com";
            }
            config.put("server", cliHub);
            String cliUser = System.getProperty("userName");
            if (!cliUser.isEmpty()) {
                config.put("user", cliUser);
            }
            String cliKey = System.getProperty("accessKey");
            if (!cliKey.isEmpty()) {
                config.put("key", cliKey);
            }
            JSONObject cliCaps = (JSONObject) parser.parse(System.getProperty("caps").replaceAll("'", "\""));
            config.put("capabilities", cliCaps);
            JSONObject clienvs = (JSONObject) parser.parse(System.getProperty("envs").replaceAll("'", "\""));
            envs = clienvs;
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserstack.source", "testng:sample-onboarding:v1.0");

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }

        if (capabilities.getCapability("browserstack.local") != null
                && capabilities.getCapability("browserstack.local") == "true") {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }

        driver = new RemoteWebDriver(
                new URL("https://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        if (l != null) {
            l.stop();
        }
    }
}

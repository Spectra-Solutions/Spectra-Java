package SpectraSprint02.SLACK;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.api.ApiTestResponse;

import java.io.IOException;

public class Exemplo {
    public static void main(String[] args) throws SlackApiException, IOException {
        Slack slack = Slack.getInstance();
        ApiTestResponse response = slack.methods().apiTest(r -> r.foo("bar"));
        System.out.println(response);
    }
}

package Spectra.Slack;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Slack {
    private static HttpClient client = HttpClient.newHttpClient();
    private static String url = "";
    private String notificaçãoSlack = "";
    private Integer fkComponente;
    private Integer fkTaxaAviso;
    private Integer fkTipoAviso;

    public Slack() {
    }

    public void sendMenssage(JSONObject content) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(url))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotificaçãoSlack() {
        return notificaçãoSlack;
    }

    public void setNotificaçãoSlack(String notificaçãoSlack) {
        this.notificaçãoSlack = notificaçãoSlack;
    }

    public Integer getFkComponente() {
        return fkComponente;
    }

    public void setFkComponente(Integer fkComponente) {
        this.fkComponente = fkComponente;
    }

    public Integer getFkTaxaAviso() {
        return fkTaxaAviso;
    }

    public void setFkTaxaAviso(Integer fkTaxaAviso) {
        this.fkTaxaAviso = fkTaxaAviso;
    }

    public Integer getFkTipoAviso() {
        return fkTipoAviso;
    }

    public void setFkTipoAviso(Integer fkTipoAviso) {
        this.fkTipoAviso = fkTipoAviso;
    }
}

package Spectra.VIEW;

import com.slack.api.model.admin.AppConfig;
import org.json.JSONObject;

import java.io.IOException;

public class SistemaSpectra {
    public static void main(String[] args) throws IOException, InterruptedException {
        IniciarSistema iniciarSistema = new IniciarSistema();

        iniciarSistema.validarLogin();
    }
}

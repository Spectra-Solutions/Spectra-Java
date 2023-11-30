package Spectra.VIEW;

import java.io.IOException;

public class SistemaSpectra {
    public static void main(String[] args) throws IOException {
        IniciarSistema iniciarSistema = new IniciarSistema();

        iniciarSistema.validarLogin();
    }
}

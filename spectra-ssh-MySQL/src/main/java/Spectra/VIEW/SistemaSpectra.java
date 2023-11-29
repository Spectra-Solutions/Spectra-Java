package Spectra.VIEW;

import Spectra.DTO.MemoriaRam;

import java.io.IOException;

public class SistemaSpectra {
    public static void main(String[] args) throws IOException, InterruptedException {
        IniciarSistema iniciarSistema = new IniciarSistema();

        iniciarSistema.validarLogin();
    }
}

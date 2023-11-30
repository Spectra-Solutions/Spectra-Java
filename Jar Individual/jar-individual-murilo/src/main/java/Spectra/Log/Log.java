package Spectra.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private String mensagem;
    private LocalDateTime dataHora;

    public Log(String mensagem, LocalDateTime dataHora) {
        this.mensagem = mensagem;
        this.dataHora = LocalDateTime.now();
    }

    public Log() {
        this.dataHora = LocalDateTime.now();
    }

    //Esse método formata a data e hora do log gerado dentro do arquivo .txt
    private String obterDataFormatadaArquivo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dataHora.format(formatter);
    }

    //Esse método formata o nome da pasta onde os arquivos .txt estão para o mês atual
    private String obterDataFormatadaTituloMes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return dataHora.format(formatter);
    }

    //Cria o diretório principal chamado logs dentro do projeto spectra-ssh
    private Path criarDiretorioPrincipal() throws IOException {
        Path caminho = Paths.get("logs");

        if (!Files.exists(caminho)) {
            Files.createDirectory(caminho);
        }

        return caminho;
    }

    //Cria os diretórios específicos de Acesso, Erro e LogEspecial dentro do diretório logs
    //Cria o diretório do mês atual dentro de cada diretório específico
    private Path criarDiretorioEspecifico(String nomeDiretorio) throws IOException {
        Path caminhoPrincipal = criarDiretorioPrincipal();
        Path caminhoEspecifico = caminhoPrincipal.resolve(nomeDiretorio);

        if (!Files.exists(caminhoEspecifico)) {
            Files.createDirectory(caminhoEspecifico);
        }

        Path subDiretorioAnoMes = caminhoEspecifico.resolve(obterDataFormatadaTituloMes());

        if (!Files.exists(subDiretorioAnoMes)) {
            Files.createDirectory(subDiretorioAnoMes);
        }

        return subDiretorioAnoMes;

    }

    //Método que gera o log com base na instância criada dentro do projeto
    //Passar como argumento dentro desse método o "tipoLog", para inserir o arquivo dentro da pasta desejada
    //Dentre elas Acesso, Erro e LogEspecial
    public void gerarLog(String tipoLog) throws IOException {

        String dataFormatadaArquivo = obterDataFormatadaArquivo();

        Path caminhoPrincipal = criarDiretorioPrincipal();
        Path caminhoEspecifico;

        // Escolhe o diretório específico com base no tipo de log
        switch (tipoLog.toLowerCase()) {
            case "acesso" -> {
                caminhoEspecifico = criarDiretorioEspecifico("LogsAcesso");
            }

            case "erro" -> {
                caminhoEspecifico = criarDiretorioEspecifico("LogsErro");
            }

            case "inovacao" -> {
                caminhoEspecifico = criarDiretorioEspecifico("LogsInovacao");
            }

            case "banco" -> {
                caminhoEspecifico = criarDiretorioEspecifico("LogsBancoDeDados");
            }

            case "slack" -> {
                caminhoEspecifico = criarDiretorioEspecifico("LogsSlack");
            }

            default -> {
                // Se não corresponder a nenhum tipo conhecido, usa o diretório principal
                caminhoEspecifico = caminhoPrincipal;
            }
        }

        LocalDateTime dataHoraAtual = LocalDateTime.now();
        String dataFormatadaAtual = dataHoraAtual.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String nomeArquivo = String.format("%s-%s.txt", dataFormatadaAtual, tipoLog);

        //Instância do arquivo
        File log = new File(caminhoEspecifico.resolve(nomeArquivo).toString());

        //Criação do arquivo caso ele não exista e caso a data seja diferente de um arquivo já existente
        if (!log.exists() || !dataFormatadaAtual.equals(dataFormatadaAtual)) {
            log.createNewFile();
        }

        //Bibliotecas FileWriter e BufferdWriter utilizadas para inserir texto dentro do arquivo
        try (FileWriter fw = new FileWriter(log, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("["+ dataFormatadaArquivo + "]" + " - " + mensagem);
            bw.newLine();

        }
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}

package com.LearnTools.LearnToolsApi.handler;

public class CampoObrigatorioException extends BussinessException {
    public CampoObrigatorioException(String message) {
        super("O campo %s é obrigatório", message);
    }

}

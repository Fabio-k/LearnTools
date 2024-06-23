package com.LearnTools.LearnToolsApi.handler;

public class CampoObrigatorioException extends BusinessException {
    public CampoObrigatorioException(String message) {
        super("O campo %s é obrigatório", message);
    }

}

package com.LearnTools.LearnToolsApi.handler;

public class BussinessException extends RuntimeException {
    public BussinessException(String mensagem) {
        super(mensagem);
    }

    public BussinessException(String mensagem, Object... params) {
        super(String.format(mensagem, params));
    }
}

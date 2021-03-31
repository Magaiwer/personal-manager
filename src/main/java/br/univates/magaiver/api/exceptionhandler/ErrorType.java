package br.univates.magaiver.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {
    ENTITY_NOT_FOUND("Entidade não encontrada"),
    INVALID_DATA("Dados inválidos"),
    BUSINESS_EXCEPTION("Violação de regra de negócio"),
    ENTITY_ALREADY_IN_USE("Entidade em uso"),
    INCOMPREENSIBLE_MESSAGE("Mensagem incompreesível"),
    INVALID_PARAMETER("Parametro inválido"),
    ACCESS_DENIED("Acesso Negado"),
    INTERNAL_SERVER_ERROR("Erro interno do servidor");

    private final String title;

    ErrorType(String title) {
        this.title = title;
    }
}


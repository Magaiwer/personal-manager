package br.univates.magaiver.domain.exception;

/**
 * @author Magaiver Santos
 */
public class ForbiddenExchangeEmail extends BusinessException {

    public ForbiddenExchangeEmail() {
        super("Não é permitido a troca de e-mail pelo metódo editar usuário");
    }
}

package br.univates.magaiver.domain.exception;

/**
 * @author Magaiver Santos
 */
public class RequiredPasswordException extends BusinessException {
    public RequiredPasswordException() {
        super("Senha é obrigatória para novo usuário");
    }
}

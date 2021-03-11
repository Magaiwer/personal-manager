package br.univates.magaiver.domain.exception;

/**
 * @author Magaiver Santos
 */
public class EmailAlreadyRegisteredException extends BusinessException {

    public EmailAlreadyRegisteredException(String email) {
        super(String.format("Email %s já está cadastrado no sistema", email));
    }
}

package Pck_Model;

public class LoginUsuarioModel {
    private String email;
    private String senha;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {

        // regex que define como um e-mail válido deve parecer
        String regexEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        // verifica se o email é nulo e se base com os requisitos do regex
        if (email != null && email.matches(regexEmail)) {
            this.email = email;
        } else {

            throw new IllegalArgumentException("E-mail com formato inválido! Use o padrão nome@email.com.");

        }
    }

    public void setSenha(String senha) {
        if(senha.isEmpty() || senha == null || senha.length() < 5){
            throw new IllegalArgumentException("Senha Inválida! Informe uma senha com pelo menos 5 dígitos.");
        } else{
            this.senha = senha;
        }

    }
}

package Pck_Model;

public class LoginUsuarioModel {
    private int idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String tipoUsuario;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        if(idUsuario < 0){
            throw new IllegalArgumentException("Erro ao manipular id de usuário.");
        } else{
            this.idUsuario = idUsuario;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isEmpty() || nome.length() <= 0){
            throw new IllegalArgumentException("O campo de 'nome' é obrigatório!");
        } else{
            this.nome = nome;
        }
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario){
        if (!tipoUsuario.toUpperCase().equals("CLIENTE") && !tipoUsuario.toUpperCase().equals("FUNCIONARIO")){
            throw new IllegalArgumentException("Erro ao definir o tipo de usuário.");
        } else{
            this.tipoUsuario = tipoUsuario;
        }
    }

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

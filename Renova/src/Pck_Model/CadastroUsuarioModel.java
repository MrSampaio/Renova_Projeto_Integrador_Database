package Pck_Model;

public class CadastroUsuarioModel {
    private String email;
    private String senha;
    private String telefone;
    private String nome;
    private String tipoUsuario;

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
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

    public void setTelefone(String telefone) {
        if (telefone.length() != 11){
            throw new IllegalArgumentException("Número de telefone inválido! Informe um telefone com o 11 digitos.");
        } else{
            this.telefone = telefone;
        }

    }

    public void setNome(String nome) {
        if(nome == null || nome.isEmpty() || nome.length() <= 0){
            throw new IllegalArgumentException("O campo de 'nome' é obrigatório!");
        } else{
            this.nome = nome;
        }
    }

    public void setTipoUsuario(String tipoUsuario) {
        if (!tipoUsuario.toUpperCase().equals("CLIENTE") || !tipoUsuario.toUpperCase().equals("FUNCIONARIO")){
            throw new IllegalArgumentException("Erro ao definir o tipo de usuário.");
        } else{
            this.tipoUsuario = tipoUsuario;
        }

    }
}

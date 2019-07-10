package glody.com.bizdirect.util;

import java.util.Date;

public class user {
    int id;
    String nome;
    String email;
    String morada;
    String telefone;
    String dtnascimento;
    Date sessionExpiryDate;
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setDtnascimento(String dtnascimento) {
        this.dtnascimento = dtnascimento;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
    public String getMorada() {
        return morada;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getDtnascimento() {
        return dtnascimento;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }


}
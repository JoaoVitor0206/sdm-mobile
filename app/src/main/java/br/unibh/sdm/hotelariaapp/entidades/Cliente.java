package br.unibh.sdm.hotelariaapp.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Cliente {
    private String nome;
    private String telefone;
    private String email;
    private String userId;
    private String cpf;

    public Cliente() {
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente that = (Cliente) o;
        return Objects.equals(getNome(), that.getNome()) &&
                Objects.equals(getTelefone(), that.getTelefone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getCpf(), that.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getTelefone(), getEmail(), getUserId(), getCpf());
    }
}


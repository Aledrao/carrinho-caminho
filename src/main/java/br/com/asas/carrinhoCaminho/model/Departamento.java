package br.com.asas.carrinhoCaminho.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "DEPARTAMENTO")
public class Departamento implements Serializable {

    private static final long serialVersionUID = 384104978454574836L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer codigo;

    @NotNull
    @NotBlank(message = "Informe o nome do departamento")
    @Column(name = "NOME", nullable = false)
    private String nome;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                '}';
    }
}

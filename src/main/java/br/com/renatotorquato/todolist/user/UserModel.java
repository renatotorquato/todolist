package br.com.renatotorquato.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

//Apenas adicionando @Data o Lombok(biblioteca do java) adiciona automaticamente os GEtters e Setters
@Data
@Entity(name = "tb_users")

public class UserModel {
    // estes são os atributos da classe e quando não definoi o tipo de modificador
    // public não funcionou.
    // na segunda aula mudou para private e ensinou como usar o setters e getters
    // para acessar e alterar estes atributos
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String name;
    private String username;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

package com.icesi.edu.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;


@Data
@Table(name = "`user`")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private UUID id;

    private String email;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    @PrePersist
    public void generateId(){
        this.id = UUID.randomUUID();
    }


}

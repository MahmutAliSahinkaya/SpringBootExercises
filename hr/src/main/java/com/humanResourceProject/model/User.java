package com.humanResourceProject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Email
    private String email;

    @NotBlank
    @NotEmpty
    private String password;

    @NotBlank
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

}

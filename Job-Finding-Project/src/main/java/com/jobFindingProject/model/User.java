package com.jobFindingProject.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "username")
        private String username;

        @Column(name = "password")
        private String password;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "phone_number")
        private String phoneNumber;

        @Column(name = "work_experience")
        private Integer workExperience;

        @Column(name = "job_experience")
        private Integer jobExperience;

        @ManyToOne
        @JoinColumn(name = "company_id")
        private Company company;


}


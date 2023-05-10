package com.humanResourceProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Employee extends User {

    private double salary;

    @Column(name = "rest_day")
    private Date restDay;

    @Column(name = "is_rest")
    private boolean isRest = false;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
}

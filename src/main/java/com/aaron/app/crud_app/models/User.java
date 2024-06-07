package com.aaron.app.crud_app.models;

import jdk.nashorn.internal.objects.annotations.Constructor;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "firstName is mandatory")
    @Column
    private String firstName;

    @NotBlank(message = "lastName is mandatory")
    @Column
    private String lastName;

    @NotBlank(message = "occupation is mandatory")
    @Column
    private String occupation;

}

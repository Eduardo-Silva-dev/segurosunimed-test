package com.example.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String zipCode;

    @NotEmpty
    @Column(nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private String complement;

    @NotEmpty
    @Column(nullable = false)
    private String neighborhood;

    @NotEmpty
    @Column(nullable = false)
    private String locality;

    @NotEmpty
    @Column(nullable = false)
    private String uf;

    @NotEmpty
    @Column(nullable = false)
    private String ddd;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

}
package com.dsoccer1980.fuel.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "type_seq_gen")
    @SequenceGenerator(name = "type_seq_gen", sequenceName = "type_id_seq")
    private long id;

    @NotBlank
    @Size(max = 5)
    @Column(name = "type", nullable = false)
    private String type;

    public FuelType(String type) {
        this.type = type;
    }
}

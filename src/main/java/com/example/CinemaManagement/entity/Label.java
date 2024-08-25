package com.example.CinemaManagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tbl_label")
@NoArgsConstructor
public class Label {

    @Id
    @Column(name = "label_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int labelId;

    @Column(name = "label_name")
    private String labelName;

    @Column(name = "description")
    private String description;

    public Label(String labelName, String description) {
        this.labelName = labelName;
        this.description = description;
    }

}

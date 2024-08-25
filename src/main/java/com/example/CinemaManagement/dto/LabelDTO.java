package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Label;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LabelDTO {
    private int labelId;
    private String labelName;
    private String description;

    public LabelDTO(Label label) {
        if (label != null) {
            this.labelId = label.getLabelId();
            this.labelName = label.getLabelName();
            this.description = label.getDescription();
        }
    }
}

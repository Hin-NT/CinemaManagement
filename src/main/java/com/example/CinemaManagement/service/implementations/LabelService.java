package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Label;
import com.example.CinemaManagement.repository.LabelRepository;
import com.example.CinemaManagement.service.interfaces.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService implements ILabelService {

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public List<Label> getAll() {
        return (List<Label>) labelRepository.findAll();
    }

    @Override
    public Label getById(Label label) {
        return labelRepository.findById(label.getLabelId()).orElse(null);
    }

    @Override
    public ResponseEntity<Label> add(Label label) {
        try {
            Label labelAdd = labelRepository.save(label);
            return ResponseEntity.status(HttpStatus.CREATED).body(labelAdd);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Label());
        }
    }

    @Override
    public ResponseEntity<String> update(Label label) {
        Optional<Label> optionalLabel = labelRepository.findById(label.getLabelId());
        if (optionalLabel.isPresent()) {
            try {
                labelRepository.save(label);
                return ResponseEntity.status(HttpStatus.OK).body("Label updated successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update seat due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Label not found!");
        }
    }

    @Override
    public ResponseEntity<String> delete(Label label) {
        Label existedLabel = this.getById(label);
        if (existedLabel != null) {
            try {
                labelRepository.delete(existedLabel);
                return ResponseEntity.status(HttpStatus.OK).body("Label deleted successfully!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete seat due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Label not found!");
        }
    }

}



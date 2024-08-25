package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.LabelDTO;
import com.example.CinemaManagement.entity.Label;
import com.example.CinemaManagement.service.interfaces.ILabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
@RequestMapping("/api/v1/labels")
public class LabelController {

    @Autowired
    private ILabelService labelService;

    @GetMapping("")
    public ResponseEntity<List<LabelDTO>> getAllLabels() {
        List<Label> labels = labelService.getAll();
        List<LabelDTO> labelDTOList = labels.stream().map(LabelDTO::new).toList();
        return ResponseEntity.ok(labelDTOList);
    }

    @GetMapping("/{labelId}")
    public ResponseEntity<LabelDTO> getLabelById(@PathVariable int labelId) {
        Label createrLabel = new Label();
        createrLabel.setLabelId(labelId);
        Label label = labelService.getById(createrLabel);
        return ResponseEntity.ok(new LabelDTO(label));
    }

    @PostMapping("")
    public ResponseEntity<String> createLabel(@RequestBody Label label) {
        return labelService.add(label);
    }

    @PutMapping("")
    public ResponseEntity<String> updateLabel(@Valid @RequestBody Label label) {
        return labelService.update(label);
    }

    @DeleteMapping("/{labelId}")
    public ResponseEntity<String> deleteLabel(@PathVariable int labelId) {
        Label label = new Label();
        label.setLabelId(labelId);
        return labelService.delete(label);
    }

}

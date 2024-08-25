package com.example.CinemaManagement.controller;

import com.example.CinemaManagement.dto.SupplierDTO;
import com.example.CinemaManagement.entity.Supplier;
import com.example.CinemaManagement.service.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private ISupplierService supplierService;

    @GetMapping("")
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.getAll();
        List<SupplierDTO> supplierDTOList = suppliers.stream().map(SupplierDTO::new).toList();
        return ResponseEntity.ok(supplierDTOList);
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable int supplierId) {
        Supplier createSupplier = new Supplier();
        createSupplier.setSupplierId(supplierId);
        Supplier supplier = supplierService.getById(createSupplier);
        if (supplier == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(new SupplierDTO(supplier));
    }

    @PostMapping("")
    public ResponseEntity<String> createSupplier(@RequestBody Supplier supplier) {
        return supplierService.add(supplier);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSupplier(@RequestBody Supplier supplier) {
        return supplierService.update(supplier);
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable int supplierId) {
        Supplier existedSupplier = new Supplier();
        existedSupplier.setSupplierId(supplierId);
        return supplierService.delete(existedSupplier);
    }

}

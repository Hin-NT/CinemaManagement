package com.example.CinemaManagement.service.implementations;

import com.example.CinemaManagement.entity.Supplier;
import com.example.CinemaManagement.repository.SupplierRepository;
import com.example.CinemaManagement.service.interfaces.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService implements ISupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getById(Supplier supplier) {
        return supplierRepository.findById(supplier.getSupplierId()).orElse(null);
    }

    @Override
    public ResponseEntity<String> add(Supplier supplier) {
        try {
            supplierRepository.save(supplier);
            return ResponseEntity.status(HttpStatus.CREATED).body("Supplier created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add supplier due to: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> update(Supplier supplier) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplier.getSupplierId());
        if (supplierOptional.isPresent()) {
            try {
                supplierRepository.save(supplier);
                return ResponseEntity.status(HttpStatus.OK).body("Supplier updated successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update supplier due to: " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }
    }

    @Override
    public ResponseEntity<String> delete(Supplier supplier) {
        Supplier existedSupplier = this.getById(supplier);
        if (existedSupplier != null) {
            supplierRepository.delete(existedSupplier);
            return ResponseEntity.status(HttpStatus.OK).body("Supplier deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found");
        }
    }

}

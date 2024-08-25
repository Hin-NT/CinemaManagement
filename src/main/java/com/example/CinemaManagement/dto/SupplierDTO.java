package com.example.CinemaManagement.dto;

import com.example.CinemaManagement.entity.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupplierDTO {
    private int supplierId;
    private String supplierName;
    private String phone;
    private String email;
    private String address;

    public SupplierDTO(Supplier supplier) {
        if (supplier != null) {
            this.supplierId = supplier.getSupplierId();
            this.supplierName = supplier.getSupplierName();
            this.phone = supplier.getPhone();
            this.email = supplier.getEmail();
            this.address = supplier.getAddress();
        }
    }

}

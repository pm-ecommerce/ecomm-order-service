package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.order_service.interfaces.IVendorService;
import com.pm.ecommerce.order_service.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService implements IVendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public Vendor registerVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Vendor finByIdVendor(int vendorId) {
        Optional<Vendor> result = vendorRepository.findById(vendorId);

        Vendor vendor = null;
        if (result.isPresent()) {
            vendor = result.get();
        } else {
            throw new RuntimeException("Did not find by vendor id - " + vendorId);
        }
        return vendor;
    }
}

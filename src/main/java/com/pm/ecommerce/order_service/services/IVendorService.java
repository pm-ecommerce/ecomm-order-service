package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Vendor;

import java.util.List;

public interface IVendorService {

    Vendor registerVendor(Vendor vendor);

    List<Vendor> getAllVendors();

    Vendor finById(int vendorId);
}

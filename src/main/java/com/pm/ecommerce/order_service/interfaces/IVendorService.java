package com.pm.ecommerce.order_service.interfaces;

import com.pm.ecommerce.entities.Vendor;

import java.util.List;

public interface IVendorService {

    Vendor registerVendor(Vendor vendor);

    List<Vendor> getAllVendors();

    Vendor finByIdVendor(int vendorId);
}

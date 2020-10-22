package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.entities.Vendor;
import com.pm.ecommerce.order_service.interfaces.IVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    @Autowired
    private IVendorService vendorService;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<Vendor>> registerVendor(@RequestBody Vendor vendorData) {
        ApiResponse<Vendor> response = new ApiResponse<>();
        try {
            Vendor vendor = vendorService.registerVendor(vendorData);

            // next update this part
            vendor.setRegistered(null);
            vendor.setStatus(null);
            vendor.setAddress(null);
            vendor.setCards(null);
            vendor.setPayment(null);

            response.setData(vendor);
            response.setMessage("Vendor registered successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all_vendors")
    public List<Vendor> getAllVendors(Vendor vendor) {
        return vendorService.getAllVendors();
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<ApiResponse<Vendor>> getVendorId(@PathVariable int vendorId) {
        ApiResponse<Vendor> response = new ApiResponse<>();

        try {
            Vendor vendor = vendorService.finByIdVendor(vendorId);

            // next update this part
            vendor.setRegistered(null);
            vendor.setStatus(null);
            vendor.setAddress(null);
            vendor.setCards(null);
            vendor.setPayment(null);

            response.setData(vendor);
            response.setMessage("Get order by id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}

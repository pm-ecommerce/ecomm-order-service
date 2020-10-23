package com.pm.ecommerce.order_service.controllers;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.entities.ApiResponse;
import com.pm.ecommerce.order_service.interfaces.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Address>> registerAddress(@RequestBody Address postData) {
        ApiResponse<Address> response = new ApiResponse<>();

        try {
            Address address = addressService.registerAddress(postData);

//            address.setAddress1(null);
//            address.setAddress2(null);
//            address.setZipcode(null);
//            address.setCity(null);
//            address.setCountry(null);

            response.setData(address);
            response.setMessage("Address registered successfully.");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Address>> getAddressId(@PathVariable int addressId) {
        ApiResponse<Address> response = new ApiResponse<>();

        try {
            Address address = addressService.findById(addressId);

            response.setData(address);
            response.setMessage("Get address by id");
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{addressId}")
//    public String deleteEmployee(@PathVariable int addressId) {
//
//        Address address = addressService.findById(addressId);
//
//        // throw exception if null
//
//        if (address == null) {
//            throw new RuntimeException("Address id not found - " + addressId);
//        }
//
//        addressService.deleteById(addressId);
//
//        return "Deleted address id - " + addressId;
//    }
    public ResponseEntity<ApiResponse<Address>> deleteAddress(@PathVariable int addressId){
        ApiResponse<Address> response = new ApiResponse<>();

        try {
            Address address = addressService.findById(addressId);
            response.setData(address);
            response.setMessage("Deleted address id - " + addressId);
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}

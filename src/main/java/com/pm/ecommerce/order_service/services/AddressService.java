package com.pm.ecommerce.order_service.services;

import com.pm.ecommerce.entities.Address;
import com.pm.ecommerce.order_service.interfaces.IAddressService;
import com.pm.ecommerce.order_service.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address registerAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(int addressId) {
        Optional<Address> result = addressRepository.findById(addressId);

        Address address = null;
        if(result.isPresent()) {
            address = result.get();
        } else {
            throw new RuntimeException("Did not find by address id - " + addressId);
        }
        return address;
    }
}

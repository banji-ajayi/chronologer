package com.critter.chronologer.services;

import com.critter.chronologer.exception.NotFoundException;
import com.critter.chronologer.repository.CustomerRepository;
import com.critter.chronologer.repository.PetRepository;
import com.critter.chronologer.user.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public CustomerEntity findCustomerById(Long customerId){
     return customerRepository.findById(customerId).orElseThrow(() ->
            new NotFoundException("Fmployee for : " + customerId + " is Not Found"));
    }

    public List<CustomerEntity> findAllCustomers(){
        return customerRepository.findAll();
    }

    public CustomerEntity saveCustomer(CustomerEntity customer){
        return customerRepository.save(customer);
    }
}

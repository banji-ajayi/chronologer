package com.critter.chronologer.services;

import com.critter.chronologer.exception.NotFoundException;
import com.critter.chronologer.pet.PetEntity;
import com.critter.chronologer.repository.CustomerRepository;
import com.critter.chronologer.repository.PetRepository;
import com.critter.chronologer.user.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customersRepository;

    public List<PetEntity> findAllPets() {
        return petRepository.findAll();
    }

    public PetEntity findPetById(Long petId) {
        return petRepository.findById(petId).orElseThrow(() ->
                new NotFoundException("Pet for : " + petId + " is Not Found"));
    }

    public List<PetEntity> findByCustomerId(Long CustomerId) {

        return petRepository.getAllPetsByCustomerId(CustomerId);
    }

    public PetEntity savePet(PetEntity pet, Long ownerId) {
        CustomerEntity customer = customersRepository.getOne(ownerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.addNewPet(pet);
        customersRepository.save(customer);
        return pet;
    }

    public List<PetEntity> findAllPetsById(List<Long> petIds) {
        return petRepository.findAllById(petIds);
    }



}

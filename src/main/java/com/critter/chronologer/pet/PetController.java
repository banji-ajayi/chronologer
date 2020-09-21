package com.critter.chronologer.pet;

import com.critter.chronologer.services.CustomerService;
import com.critter.chronologer.services.PetService;
import com.critter.chronologer.user.CustomerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetEntity pet = new PetEntity();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        CustomerEntity customer = customerService.findCustomerById(petDTO.getOwnerId());
        if(customer.getPets() == null) customer.setPets(new ArrayList<>());

        return getPetDTO(petService.savePet(pet, petDTO.getOwnerId()));

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return getPetDTO(petService.findPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets() {
        List<PetEntity> pets = petService.findAllPets();
        return pets.stream().map(this::getPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.findByCustomerId(ownerId).stream().map(pet -> {
            PetDTO petDTO = new PetDTO();
            BeanUtils.copyProperties(pet, petDTO);
            petDTO.setOwnerId(ownerId);
            return petDTO;
        }).collect(Collectors.toList());
    }


    private PetDTO getPetDTO(PetEntity pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getType());
        petDTO.setNotes(pet.getNotes());
        petDTO.setBirthDate(pet.getBirthDate());

        return petDTO;

    }
}
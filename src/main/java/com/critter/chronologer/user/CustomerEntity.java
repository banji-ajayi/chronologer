package com.critter.chronologer.user;

import com.critter.chronologer.pet.PetEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class CustomerEntity {

    @Id
    @GeneratedValue
    private Long id;


    private String name;
    private String notes;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<PetEntity> pets;

    public void addNewPet(PetEntity pet) {
        pets.add(pet);
    }

    public CustomerEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<PetEntity> getPets() {
        return pets;
    }

    public void setPets(List<PetEntity> pets) {
        this.pets = pets;
    }
}

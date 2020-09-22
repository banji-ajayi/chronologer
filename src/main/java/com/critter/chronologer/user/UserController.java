package com.critter.chronologer.user;

import com.critter.chronologer.pet.PetEntity;
import com.critter.chronologer.services.CustomerService;
import com.critter.chronologer.services.EmployeeService;
import com.critter.chronologer.services.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerEntity customer = new CustomerEntity();
        BeanUtils.copyProperties(customerDTO, customer);
        CustomerEntity customerSaved = customerService.saveCustomer(customer);
        BeanUtils.copyProperties(customerSaved, customerDTO);

        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerEntity> customers = customerService.findAllCustomers();
        return customers.stream().map(this::getCustomerDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return getCustomerDTO(customerService.findCustomerByPetId(petId));
    }


    private CustomerDTO getCustomerDTO(CustomerEntity customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setNotes(customer.getNotes());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());

        if(customer.getPets() != null) {
            List<Long> petIds = customer.getPets().stream()
                    .map(PetEntity::getId)
                    .collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(employeeDTO.getName());
        if(employeeDTO.getSkills() != null){
            employee.setEmployeeSkills(employeeDTO.getSkills());
        }
        if(employeeDTO.getDaysAvailable() != null) {
            employee.setAvailableDaysForEmployee(employeeDTO.getDaysAvailable());
        }
        return getEmployeeDTO(employeeService.save(employee));
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeEntity employee = employeeService.findEmployeeById(employeeId);
        return getEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@PathVariable EmployeeRequestDTO employeeDTO) {
        List<EmployeeEntity> employees = employeeService.
                findEmployeesBasedOnAvailability(employeeDTO.getSkills(), employeeDTO.getDate());
        return employees.stream().map(this::getEmployeeDTO).collect(Collectors.toList());
    }

    private EmployeeDTO getEmployeeDTO(EmployeeEntity employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        if(employee.getEmployeeSkills() != null) {
             employeeDTO.setSkills(employee.getEmployeeSkills());
        }
        if(employee.getAvailableDays() != null) {
            employeeDTO.setDaysAvailable(employee.getAvailableDays());
        }
        return employeeDTO;
    }


}

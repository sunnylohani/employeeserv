package com.paypal.bfs.test.employeeserv.service.impl;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.EmployeeServiceException;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployeeById(Integer id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        if (employeeEntity.isPresent()) {
            Employee employee = new Employee();
            employee.setId(employeeEntity.get().getId());
            employee.setFirstName(employeeEntity.get().getFirstName());
            employee.setLastName(employeeEntity.get().getLastName());
            employee.setDateOfBirth(employeeEntity.get().getDateOfBirth());
            Address address = new Address();
            address.setLine1(employeeEntity.get().getAddressLine1());
            address.setLine2(employeeEntity.get().getAddressLine2());
            address.setCity(employeeEntity.get().getCity());
            address.setState(employeeEntity.get().getState());
            address.setCountry(employeeEntity.get().getCountry());
            address.setZipCode(employeeEntity.get().getZipCode());
            employee.setAddress(address);
            return employee;
        }

        throw new EmployeeServiceException("Invalid employee resource id");
    }

    @Override
    public Integer createEmployee(Employee employee) {
        log.info("Received create request for employee: {}", employee);
        EmployeeEntity employeeEntity = EmployeeEntity.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .dateOfBirth(employee.getDateOfBirth())
                .addressLine1(employee.getAddress().getLine1())
                .addressLine2(employee.getAddress().getLine2())
                .city(employee.getAddress().getCity())
                .state(employee.getAddress().getState())
                .country(employee.getAddress().getCountry())
                .zipCode(employee.getAddress().getZipCode())
                .build();

        employeeEntity = employeeRepository.save(employeeEntity);
        return employeeEntity.getId();
    }
}

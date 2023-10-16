package com.stefansteuge.customer;

import com.stefansteuge.exception.DuplicateResourceException;
import com.stefansteuge.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customer getCustomerById(Integer customerId) {
        return customerDao.getCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(customerId)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        validation(customerRegistrationRequest.email(), "Email already exists");

        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );

        customerDao.insertCustomer(customer);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest)  {
                Customer customer = getCustomerById(customerId);

                boolean changes = false;

                if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
                    customer.setName(updateRequest.name());
                    changes = true;
        }
                if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
                    customer.setAge(updateRequest.age());
                    changes = true;
                }
                if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
                    validation(updateRequest.email(), "Email already taken");
                    customer.setEmail(updateRequest.email());
                    changes = true;
                }

                if (!changes) {
                    throw new ResourceNotFoundException("No data changes found");
                }
                customerDao.updateCustomer(customer);
    }

    public void deleteCustomer(Integer customerId) {
        if (!customerDao.existsPersonById(customerId)){
            throw new ResourceNotFoundException(
                    "Customer by given id [%customerId] does not exist ".formatted(customerId));
        }

        customerDao.deleteById(customerId);
    }

    private void validation(String updateRequest, String Email_already_taken) {
        if (customerDao.existPersonByEmail(updateRequest)) {
            throw new DuplicateResourceException(Email_already_taken);
        }
    }
}

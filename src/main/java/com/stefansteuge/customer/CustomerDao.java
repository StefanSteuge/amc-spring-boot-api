package com.stefansteuge.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Integer id);

    void insertCustomer(Customer customer);

    boolean existPersonByEmail(String email);

    boolean existsPersonById(Integer id);

    void updateCustomer(Customer update);

    void deleteById(Integer id);


}

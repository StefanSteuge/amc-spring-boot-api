package com.stefansteuge.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(1, "Alex", "alex@gmail.com", 21);
        Customer jamila = new Customer(2, "Jamila", "jamila@gmail.com", 23);
        customers.add(alex);
        customers.add(jamila);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> getCustomerById(Integer id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existPersonByEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonById(Integer id) {
        return customers.stream()
                .anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void updateCustomer(Customer update) {
        customers.add(update);
    }

    @Override
    public void deleteById(Integer id) {
        customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(customers::remove);
    }
}

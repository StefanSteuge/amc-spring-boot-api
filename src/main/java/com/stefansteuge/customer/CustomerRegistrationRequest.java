package com.stefansteuge.customer;

public record CustomerRegistrationRequest (
        String name,
        String email,
        Integer age
){
}

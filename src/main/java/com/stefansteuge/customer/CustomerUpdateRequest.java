package com.stefansteuge.customer;

public record CustomerUpdateRequest (
        String name,
        String email,
        Integer age
){
}

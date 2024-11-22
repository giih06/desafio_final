package com.desafio_final.desafio_final.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    @PersistenceContext
    private EntityManager entityManager;

    private String column;
    private Class<?> entity;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        this.column = constraintAnnotation.column();
        this.entity = constraintAnnotation.entity();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Permitir valores nulos, caso necessário
        }
        String query = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value", entity.getSimpleName(), column);
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("value", value)
                .getSingleResult();
        return count == 0;
    }
}

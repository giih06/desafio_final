package com.desafio_final.desafio_final.service.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValueValidator.class)
public @interface UniqueValue {
    String message() default "Essa coluna tem que ser única";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String column(); // Indica qual coluna verificar
    Class<?> entity(); // Classe da entidade no banco de dados
}
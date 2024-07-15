package com.example.orders.model;

import com.example.orders.dto.MyCustomAnnotation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.experimental.FieldNameConstants;



@Entity
@FieldNameConstants
public class User {
    @Id
    private Long id;

    @MyCustomAnnotation(columnName = Fields.firstName)
    private String firstName;

    @MyCustomAnnotation(columnName = Fields.lastName)
    private String lastName;
}

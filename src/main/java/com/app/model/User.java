package com.app.model;

import com.app.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String firstName;
    private String lastName;
    private Integer age;
    private Gender gender;

    @Override
    public String toString() {
        return String.format("First name: %s%nLast name: %s%nAge: %d%nGender: %s%n",
                firstName, lastName, age, gender.toString().toLowerCase());
    }
}

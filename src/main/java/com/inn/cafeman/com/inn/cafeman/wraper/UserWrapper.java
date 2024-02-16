package com.inn.cafeman.com.inn.cafeman.wraper;

import lombok.Data;
import lombok.NoArgsConstructor;

//Annotation: getters and setters inclusion.
@Data
@NoArgsConstructor
public class UserWrapper {
    private Integer id;
    private String name;
    private String email;
    private String contactNumber;
    private String status;
    
    public UserWrapper(Integer id, String name, String email, String contactNumber, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.status = status;
    }
}

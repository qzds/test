package com.example.entity.user;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class AccountUser {
    int id;
    @Column("studentId")
    String username;
    String email;
    String name;
    String major;
    String student_class;
    String grade;
    String phone;
}
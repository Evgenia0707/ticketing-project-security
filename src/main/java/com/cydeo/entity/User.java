package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
//@Where(clause = "is_deleted=false")         // SELECT * FROM users WHERE id = 4 AND is_deleted = false; (don't choose if deleted)
//if delete manager - all tasks will be in problem - del all under manager then del manager
public class User extends BaseEntity {

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String userName;

    private String passWord;
    private boolean enabled;
    private String phone;

    @ManyToOne
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

 }

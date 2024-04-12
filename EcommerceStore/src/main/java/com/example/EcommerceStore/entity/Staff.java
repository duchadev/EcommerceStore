package com.example.EcommerceStore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff")
public class Staff {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="staff_id")
  private int ID;

  @NotEmpty(message = "Name is required")
  @Column(name="staff_name")

  private String name;
  @NotEmpty(message = "Address is required")
  @Column(name="staff_address")
  private String address;
  @Pattern(regexp="(^$|[0-9]{10})", message = "Phone number must be 10 digits")
  @Column(name="staff_phone_number")
  private String phone;
  @NotNull(message = "Birthday is required")
  @Past(message = "Birthday must be in the past")
  @Column(name="staff_birthday")
  private Date birthday;
  @NotEmpty(message = "Password is required")
  @Column(name="password")
  private String password;

  @JsonManagedReference(value = "staff_schedule")
  @OneToMany(mappedBy = "staff", cascade = CascadeType.REMOVE)
  private List<Schedule> schedules;
}

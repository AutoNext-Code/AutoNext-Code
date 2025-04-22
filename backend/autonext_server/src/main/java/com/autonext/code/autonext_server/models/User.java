package com.autonext.code.autonext_server.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.*;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.autonext.code.autonext_server.models.enums.Role;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private int id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String surname;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Car> cars;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Booking> bookings;

  @ManyToOne
  @JoinColumn(name = "work_center_id", nullable = true)
  private WorkCenter workCenter;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Strike> strikes;
  
  @Column(name = "job_position")
  private String jobPosition;

  @Column(nullable = false)
  private Role role;

  @Column(nullable = false)
  private boolean isBanned;

  @Column(nullable = false)
  private boolean emailConfirm;

  @Column(name = "confirmation_token", unique = true)
  private String confirmationToken;

  @Column
  private boolean penalized;


  public User() {
    this.role = Role.User;
    this.isBanned = false;
    this.emailConfirm = false;
    this.jobPosition = "";
    this.confirmationToken = null;
    this.penalized=false;
  }

  public User(String email, String name, String surname, String password, boolean emailConfirm) {
    super();
    this.email = email;
    this.name = name;
    this.surname = surname;
    this.password = password;
    this.emailConfirm = emailConfirm;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getJobPosition() {
    return jobPosition;
  }

  public void setJobPosition(String jobPosition) {
    this.jobPosition = jobPosition;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isBanned() {
    return isBanned;
  }

  public void setBanned(boolean isBanned) {
    this.isBanned = isBanned;
  }

  public boolean isEmailConfirm() {
    return emailConfirm;
  }

  public void setEmailConfirm(boolean emailConfirm) {
    this.emailConfirm = emailConfirm;
  }

  public String getConfirmationToken() {
    return confirmationToken;
  }

  public void setConfirmationToken(String confirmationToken) {
    this.confirmationToken = confirmationToken;
  }

  public void setCars(List<Car> cars) {
    this.cars = cars;
  }

  public List<Car> getCars() {
    return cars;
  }

  public List<Booking> getBookings() {
    return bookings;
  }

  public void setBookings(List<Booking> bookings) {
    this.bookings = bookings;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(() -> this.role.name());
  }

  @Override
  public String getUsername() {
    return email;
  }

  public WorkCenter getWorkCenter() {
    return workCenter;
  }

  public void setWorkCenter(WorkCenter workCenter) {
    this.workCenter = workCenter;
  }

  public List<Strike> getStrikes() {
    return strikes;
  }

  public void setStrikes(List<Strike> strikes) {
    this.strikes = strikes;
  }

  public void addStrike(Strike strike){
    this.strikes.add(strike);
  }


  public boolean isPenalized() {
    return penalized;
  }

  public void setPenalized(boolean penalized) {
    this.penalized = penalized;
  }

  
}

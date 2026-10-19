package com.onerivet.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.onerivet.util.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "[User]", schema = Constants.USER_SCHEMA)
public class User {
	
	@Id
    @Column(name = "[UserId]")
    private String userId;

    @Column(name = "[FirstName]")
    private String firstName;

    @Column(name = "[LastName]")
    private String lastName;
 
    @Column(name = "[DateOfBirth]")
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[GenderId]")
    private Gender gender;

    @Column(name = "[Email]")
    private String email;

    @Column(name = "[PhoneNumber]")
    private String phoneNumber;

    @Column(name = "[Address]")
    private String address;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "[RoleId]")
    private Role role;
    
    @Column(name = "[CreatedDate]")
	private LocalDateTime createdDate;
      
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "[ModifiedById]")
	private User modifiedById;

	@Column(name = "[ModifiedDate]")
	private LocalDateTime modifiedDate;
	
	@Column(name = "[DeletedDate]")
	private LocalDateTime deletedDate;
	
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public User getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(User modifiedById) {
		this.modifiedById = modifiedById;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
	
}
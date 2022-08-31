package com.vm.management.vmmanagement.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	@Column(columnDefinition = "boolean default false")
	private boolean Is_Vm_Allocated;
	@Column
	private String ipallocated;

	public User() {

	}

	public User(Integer id, boolean is_Vm_Allocated) {
		super();
		this.id = id;
		Is_Vm_Allocated = is_Vm_Allocated;
		this.ipallocated = ipallocated;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean Is_Vm_Allocated() {
		return Is_Vm_Allocated;
	}

	public void setIs_Vm_Allocated(boolean is_Vm_Allocated) {
		Is_Vm_Allocated = is_Vm_Allocated;
	}

	public String getIpallocated() {
		return ipallocated;
	}

	public void setIpallocated(String ipallocated) {
		this.ipallocated = ipallocated;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", Is_Vm_Allocated=" + Is_Vm_Allocated + ", ipallocated=" + ipallocated + "]";
	}

}

package com.vm.management.vmmanagement.vm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vm_details")
public class Vm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer vmid;
	@Column
	private String ip;
	@Column(columnDefinition = "boolean default true")
	private Boolean is_Vm_Available;
	@Column
	private Integer userid;

	public Vm() {

	}

	public Vm(Integer vmid, String ip, Boolean is_Vm_Available, Integer userid) {
		super();
		this.vmid = vmid;
		this.ip = ip;
		this.userid = userid;
		this.is_Vm_Available = is_Vm_Available;
	}

	public Integer getVmid() {
		return vmid;
	}

	public void setVmid(Integer vmid) {
		this.vmid = vmid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Boolean getis_Vm_Available() {
		return is_Vm_Available;
	}

	public void setis_Vm_Available(Boolean is_Vm_Available) {
		this.is_Vm_Available = is_Vm_Available;
	}

	@Override
	public String toString() {
		return "Vm [vmid=" + vmid + ", ip=" + ip + ", userid=" + userid + ", is_Vm_Available=" + is_Vm_Available + "]";
	}

}

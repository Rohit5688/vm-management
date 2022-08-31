package com.vm.management.vmmanagement.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vm.management.vmmanagement.vm.Vm;

public interface VmRepository extends JpaRepository<Vm, Integer> {

}

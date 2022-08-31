package com.vm.management.vmmanagement.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vm.management.vmmanagement.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

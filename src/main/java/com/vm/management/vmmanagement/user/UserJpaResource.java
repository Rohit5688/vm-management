package com.vm.management.vmmanagement.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vm.management.vmmanagement.exception.UserNotFoundException;
import com.vm.management.vmmanagement.exception.VmAllocatedToOtherUserExceptiom;
import com.vm.management.vmmanagement.exception.VmNotAvailableFoundException;
import com.vm.management.vmmanagement.exception.VmNotAvailableToCheckedInException;
import com.vm.management.vmmanagement.jpa.UserRepository;
import com.vm.management.vmmanagement.jpa.VmRepository;
import com.vm.management.vmmanagement.vm.Vm;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private VmRepository vmRepository;

	public UserJpaResource(UserRepository userRepository, VmRepository vmRepository) {
		this.userRepository = userRepository;
		this.vmRepository = vmRepository;
	}

	private Vm vm;

	@GetMapping("/")
	public String landingPage() {
		return "Welcome to VM Management System for refernce open following url in browser http://localhost:8080/swagger-ui/index.html";
	}

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	// http://localhost:8080/users

	// EntityModel
	// WebMvcLinkBuilder

	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id:" + id);

		EntityModel<User> entityModel = EntityModel.of(user.get());

		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));

		return entityModel;
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/users/{id}/vms")
	public String retrieveVmsForUser(@PathVariable Integer id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("id: " + id + " Invalid User ID, Please check your USER ID");
		String out = "IP allocate to user: " + user.get().getIpallocated();
		return out;

	}

	@GetMapping("/vms")
	public List<Vm> retrievevms() {
		return vmRepository.findAll();
	}

	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@PostMapping("/users/{id}/vmcheckout")
	@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "VM Allocated")
	public String checkoutVMForUser(@PathVariable Integer id, @RequestBody Vm vm) throws VmNotAvailableFoundException {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException("id:" + id);
		EntityModel<User> entityModel = EntityModel.of(user.get());
		Optional<Vm> vmUser = vmRepository.findById(vm.getVmid());
		if (!vmUser.get().getis_Vm_Available()) {
			throw new VmNotAvailableFoundException("VM_id: " + vm.getVmid() + " is not available at this moment");
		}
		vm.setUserid(user.get().getId());
		vm.setis_Vm_Available(false);
		vm.setIp(vmUser.get().getIp());
		vmRepository.save(vm);
		user.get().setIpallocated(vmUser.get().getIp());
		user.get().setIs_Vm_Allocated(true);
		User userinuse = userRepository.save(user.get());
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(vm.getVmid())
				.toUri();

		return user.get().getIpallocated() + " is allocated";

	}

	@PostMapping("/users/{id}/vmcheckin")
	@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "VM Allocated")
	public ResponseEntity<Object> checkinVMForUser(@PathVariable Integer id, @RequestBody Vm vm)
			throws VmNotAvailableFoundException, VmAllocatedToOtherUserExceptiom, VmNotAvailableToCheckedInException {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException("id: " + id + " Invalid User ID, Please check your USER ID");
		EntityModel<User> entityModel = EntityModel.of(user.get());
		Optional<Vm> vmUser = vmRepository.findById(vm.getVmid());
		if (vmUser.get().getis_Vm_Available()) {
			throw new VmNotAvailableToCheckedInException("VM_id: " + vm.getVmid() + ", VM_IP: " + vmUser.get().getIp()
					+ " is avaiable at the momment for use");
		}
		if (user.get().getId() != vmUser.get().getUserid()) {
			throw new VmAllocatedToOtherUserExceptiom("VM_id: " + vm.getVmid()
					+ " is allocated to other user, you can not checkin this VM, Check your VM ID");
		}
		vm.setUserid(null);
		vm.setis_Vm_Available(true);
		vm.setIp(vmUser.get().getIp());
		vmRepository.save(vm);
		user.get().setIs_Vm_Allocated(false);
		User userinuse = userRepository.save(user.get());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vm.getVmid())
				.toUri();
		return ResponseEntity.created(location).build();

	}
}

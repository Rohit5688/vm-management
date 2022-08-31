package com.vm.management.vmmanagement;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestartController {

	@PostMapping("/restart")
	public void restart() {
		VmManagementApplication.restart();
	}
}

Feature: Test VM-Management

  Background: 
    * url 'http://localhost:8080'
    * configure connectTimeout = 500000
    * configure readTimeout = 500000
    * configure proxy = 'http://localhost:8080'

  Scenario: Verify a user can get list of users
    Given path 'users'
    And header Accept = 'application/json'
    When method GET
    Then status 200
    And match response contains {"ipallocated":[],"id":1},{"ipallocated":[""],"id":2},{"ipallocated":[""],"id":3},{"ipallocated":[""],"id":4},{"ipallocated":[""],"id":5}

  Scenario: Verify user is able to get user by id
    Given path 'users/1'
    And header Accept = 'application/json'
    When method GET
    Then status 200
    And match response contains {"_links":{"all-users":{"href":"http://localhost:8080/users"}},"ipallocated":[]}

  Scenario: Verify application gives error when user try to get empty/null user
    Given path 'users/'
    And header Accept = 'application/json'
    When method GET
    Then status 404
    And match response contains {"error":"Not Found"}

  Scenario: Verify a user can get list of VMs
    Given path 'vms'
    And header Accept = 'application/json'
    When method GET
    Then status 200
    And match response contains {"vmid":1,"ip":"1,1.1.1","is_Vm_Available":true,"userid":null},{"vmid":2,"ip":"2,2.2.2","is_Vm_Available":true,"userid":null},{"vmid":3,"ip":"3,3.3.3","is_Vm_Available":true,"userid":null},{"vmid":4,"ip":"4,4.4.4","is_Vm_Available":true,"userid":null},{"vmid":5,"ip":"5,5.5.5","is_Vm_Available":true,"userid":null}

  Scenario: Verify user is able to checkout VM
    Given path '/users/1/vmcheckout'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}

  Scenario: Verify user is unable to Checkout in use VM by another user
    Given path '/users/2/vmcheckout'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 410
    And match response contains {"message":"VM_id: 1 is not available at this moment"}

  Scenario: Veerify user is unable to Checkin VM allocated to another user
    Given path '/users/2/vmcheckin'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 406
    And match response contains {"message": "VM_id: 1 is allocated to other user, you can not checkin this VM, Check your VM ID"}

  Scenario: Verify user is able to checkin VM allocated to user
    Given path '/users/1/vmcheckin'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}

  Scenario: Verify user is unable to checkout VM is VMs are allocated to pther users
    Given path '/users/1/vmcheckout'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    ##
    Given path '/users/1/vmcheckout'
    And request {"vmid":2}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    ##
    Given path '/users/1/vmcheckout'
    And request {"vmid":3}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    ##
    Given path '/users/1/vmcheckout'
    And request {"vmid":4}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    ##
    Given path '/users/1/vmcheckout'
    And request {"vmid":5}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    ##
    Given path '/users/2/vmcheckout'
    And request {"vmid":5}
    And header Accept = 'application/json'
    When method POST
    Then status 410
    And match response contains {"message":"VM_id: 5 is not available at this moment"}
    ##
    Given path '/users/1/vmcheckin'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}
    ##
    Given path '/users/1/vmcheckin'
    And request {"vmid":2}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}
    ##
    Given path '/users/1/vmcheckin'
    And request {"vmid":3}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}
    ##
    Given path '/users/1/vmcheckin'
    And request {"vmid":4}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}
    ##
    Given path '/users/1/vmcheckin'
    And request {"vmid":5}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}

  Scenario: Verify user is able to retrive VM info in case of system restart
    Given path '/users/1/vmcheckout'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    #
    * def sleep = function(pause){ java.lang.Thread.sleep(pause*1000) }
    Given path '/users/1/vms'
    And header Accept = 'application/json'
    When method GET
    Then status 200
    And match response contains "IP allocate to user: [1,1.1.1]"
    * print 'before'
    * call sleep 5
    * print 'after'
    #
    Given path '/restart'
    And header Accept = 'application/json'
    When method POST
    Then status 200
    #
    * def sleep = function(pause){ java.lang.Thread.sleep(pause*1000) }
    * print 'before'
    * call sleep 5
    * print 'after'
    Given path '/users/1/vms'
    And retry until responseStatus == 200
    And header Accept = 'application/json'
    When method GET
    Then status 200
    And match response contains "IP allocate to user: [1,1.1.1]"
    #
    Given path '/users/1/vmcheckin'
    And request {"vmid":1}
    And header Accept = 'application/json'
    When method POST
    Then status 202
    And match response contains {"error":"Accepted"}

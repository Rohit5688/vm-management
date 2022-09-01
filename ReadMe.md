#vm-management

######Requirements
```
Assume you are the administrator of a cloud which hosts some finite number of Virtual Machines. Users
of your cloud can borrow or check-out VMs for use. Once they are done using it, they can check-in the VM
back. Once a VM is checked in, as an administrator, you should perform some cleanup on the VM and
then return it back to the pool of VMs. Some points to keep in mind:
• Implement a system that will allow your clients to check-out and check-in VMs from a pool of n
VMs (n <= 100) and help you administer usage of VMs in your cloud.
• When clients need a VM for use, they will call a checkout method provided by your VM
reservation system. They should get the IP of a VM they can use, along with any other details
you may need.
• When clients are done using the VM, they will call a check-in method provided by your VM
reservation system.
• If a client requests a VM and no VM is available to be checked out, then your system should let
your clients know accordingly, so they may retry after some time.
• The same VM cannot be checked out by two clients at the same time.
• A VM checked out by one client cannot be checked in by some other client.
• If your system stops running for some reason and needs to be restarted, then it should continue
to know all the information about VMs that have been already checked out and VMs that are
available.
• Make sure you write appropriate test cases for the assignment. It would be great if you could
indicate the code coverage achieved by these test cases.
• The program should be implemented as a server against which the user can use the functionality
with REST APIs.
• Please host the code in a Github repository with clear information on how to use as part of the
README.
• Note: You can mimic VMs in the form of objects.
```

######Tools used:
```
Eclipse
Java v17
maven v3.8.6
Spring boot v3
Karate v5
karate-Junit v5
Cucumber reporting v5.3.1
```
######How to use?:

```
1. Clone repo to eclipse
2. Build project
3. Run "/vm-management/src/main/java/com/vm/management/vmmanagement/VmManagementApplication.java" 
as Java Application or Run maven install it will create jar, then you can run jar using java- jar location/to/jar
4. The application will run on localhost:8080
5. To check documentation related to end points refer url: http://localhost:8080/swagger-ui/index.html
6. Once the application is up and running run mvn clean install to run karate tests
7. Once the execution is done you can refer test result at "/vm-management/target/cucumber-html-reports/overview-features.html"
8. Open HTML file in Chrome to see details

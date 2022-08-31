CREATE TABLE IF NOT EXISTS user_details
(
   id INTEGER AUTO_INCREMENT ,
   Is_Vm_Allocated boolean,
   ipallocated VARCHAR (128)
);
CREATE TABLE IF NOT EXISTS vm_details
(
   vmid INTEGER AUTO_INCREMENT,
   ip VARCHAR (128) ,
   is_Vm_Available boolean,
   userid INTEGER
);



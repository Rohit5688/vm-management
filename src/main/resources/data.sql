
MERGE INTO user_details 
(id,ipallocated) VALUES (1,'');
MERGE INTO user_details 
(id,ipallocated) VALUES (2,'');
MERGE INTO user_details 
(id,ipallocated) VALUES (3,'');
MERGE INTO user_details 
(id,ipallocated) VALUES (4,'');
MERGE INTO user_details 
(id,ipallocated) VALUES (5,'');

MERGE
   INTO  user_details u
   USING vm_details v
   ON  ( v.userid = u.id and
         v.ip is not null)
WHEN MATCHED
THEN
   UPDATE
   SET   u.ipallocated= v.ip;


MERGE into vm_details(vmid,ip)
values(1,'1,1.1.1');
MERGE into vm_details(vmid,ip)
values(2,'2,2.2.2');
MERGE into vm_details(vmid,ip)
values(3,'3,3.3.3');
MERGE into vm_details(vmid,ip)
values(4,'4,4.4.4');
MERGE into vm_details(vmid,ip)
values(5,'5,5.5.5');

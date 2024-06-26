ALTER TABLE EMPLOYEE
ADD CONSTRAINT fk_department_id
FOREIGN KEY (DEPARTMENT_ID)  REFERENCES DEPARTMENT(ID) ON DELETE SET NULL;

ALTER TABLE EMPLOYEE_ROLE
ADD CONSTRAINT fk_employee_login
FOREIGN KEY (EMPLOYEE_LOGIN) REFERENCES EMPLOYEE(LOGIN);
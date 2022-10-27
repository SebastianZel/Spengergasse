create or replace FUNCTION change_department_salary (
    p_last_name       IN                employees.last_name%TYPE,
    p_department_id   IN                employees.department_id%TYPE
) RETURN employees.last_name%TYPE AS
/* Variables */

    emp_null EXCEPTION;
    emp_does_not_exist EXCEPTION;
    dep_does_not_exist EXCEPTION;
    too_many_emps EXCEPTION;
    selected_emp_last_name   employees.last_name%TYPE;
    selected_emp             employees.employee_id%TYPE;
    selected_department      employees.department_id%TYPE;
BEGIN
/* Checks for nulls */
    IF p_last_name IS NULL THEN
        RAISE emp_null;
    END IF;
    IF p_department_id IS NULL THEN
        RAISE emp_null;
    END IF;
    BEGIN
        SELECT
            last_name
        INTO 
			selected_emp
        FROM
            employees
        WHERE
            last_name = p_last_name;

    EXCEPTION
        WHEN no_data_found THEN
            RAISE emp_does_not_exist;
        WHEN too_many_rows THEN
            RAISE too_many_emps;
    END;
/* set employees and the department you are looking for, since the department id could be not exist, and as multiple 
    employees could have the same last name or not exist*/

    BEGIN
        SELECT
            department_id
        INTO 
			selected_department
        FROM
            departments
        WHERE
            department_id = p_department_id;

    EXCEPTION
        WHEN no_data_found THEN
            RAISE dep_does_not_exist;
    END;

    BEGIN
        SELECT
            employee_id
        INTO 
			selected_emp
        FROM
            employees
        WHERE
            last_name = selected_emp_last_name;

    EXCEPTION
        WHEN no_data_found THEN
            RAISE emp_does_not_exist;
    END;
/* Update */

    UPDATE employees
    SET
        salary = (
            SELECT
                salary
            FROM
                employees
            WHERE
                employee_id = selected_emp
        )
    WHERE
        department_id = selected_department;

    RETURN selected_emp_last_name;
    /* exception handling */
EXCEPTION
    WHEN emp_null THEN
        raise_application_error(-20201, 'Employee input parameters not given');
    WHEN emp_does_not_exist THEN
        raise_application_error(-20201, 'Named Employee does not exist');
    WHEN too_many_emps THEN
        raise_application_error(-20201, 'Multiple employees with given name exist');
    WHEN dep_does_not_exist THEN
        raise_application_error(-20201, 'No department with given dep id exist');
END;




/// TEST /// 



CREATE OR REPLACE PROCEDURE change_department_salary_test AS 
/* emp 116 und 118 sind im selben department, haben aber unterschiedlich salary. nach der function mit emp
 id 118 sollte auch emp 116 2600 als salary haben*/

    v_salary         employees.salary%TYPE; /* variablen initialisieren */
    v_salary2        employees.salary%TYPE;
    v_salaryreturn   employees.salary%TYPE;
    himuro           CHAR(25) := 'himuro'; 
BEGIN
  /* positiven Testfall */
    SELECT
        salary
    INTO v_salary
    FROM
        employees
    WHERE
        employee_id = 118;

    SELECT
        salary
    INTO v_salary2
    FROM
        employees
    WHERE
        employee_id = 116;

  /* positiven Testfall */

    v_salaryreturn := change_department_salary('himuro', 30);


  /* Stimmts? */
    dbms_output.put_line('v_salary should be v_salary2');
    IF v_salary = v_salary2 THEN
        dbms_output.put_line('OK');
    ELSE
        dbms_output.put_line('NOT! OK');
    END IF;


  /* NULL Werte */

    v_salaryreturn := change_department_salary(NULL, 30);

  /*Employee welchen es nicht gibt*/
    v_salaryreturn := change_department_salary('Krendelsberger', 30);
  /*Department welches es nicht gibt*/
    v_salaryreturn := change_department_salary('Himuro', 123456);
    ROLLBACK;
END change_department_salary_test;
DELETE
FROM
    BASE.DEPARTMENT T1
WHERE
    T1.DEPARTMENT_ID = :departmentId
AND T1.DEPARTMENT_NAME = :departmentName
AND T1.PARENT_DEPARTMENT_ID = :parentDepartmentId
SELECT
    T1.DEPARTMENT_ID,
    T1.DEPARTMENT_NAME,
    T1.PARENT_DEPARTMENT_ID,
    T1.DEPARTMENT_MANAGER
FROM
    BASE.DEPARTMENT T1
${CONDITIONS}
ORDER BY
    T1.DEPARTMENT_ID ASC
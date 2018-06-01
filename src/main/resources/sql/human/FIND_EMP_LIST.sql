SELECT
    T1.*,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'emptype'
        AND T2.VALUE = T1.EMPTYPE) AS EMPTYPEDISPLAY,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'jobstatus'
        AND T2.VALUE = T1.JOBSTATUS) AS JOBSTATUSDISPLAY,
    T3.DEPARTMENT_NAME,
    T4.LOCATION_NAME
FROM
    HR.EMPLOYEE T1
JOIN
    BASE.DEPARTMENT T3
ON
    T1.DEPARTMENT_ID = T3.DEPARTMENT_ID
LEFT JOIN
    HR.LOCATION T4
ON
    T1.LOCATION_ID = T4.LOCATION_ID
${CONDITIONS}
ORDER BY
    T1.EMP_ID ASC
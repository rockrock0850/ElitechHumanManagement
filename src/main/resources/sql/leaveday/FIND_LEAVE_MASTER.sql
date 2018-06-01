SELECT
    *
FROM
    HR.LEAVEDAY_M T1
WHERE
    T1.EMP_ID = :empId
AND T1.YEARS = :years
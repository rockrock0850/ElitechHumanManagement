SELECT DISTINCT
    T1.*,
    T3.LEAVE_NAME,
    T3.IS_DOCUMENT,
    T3.IS_SUBSTITUTE,
    T4.EMP_CNAME,
    T4.EMPTYPE,
    T4.DEPARTMENT_ID,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'process'
        AND T1.PROCESS = T2.VALUE
        AND T2.STATUS = '1') AS PROCESS_DISPLAY,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'approve_status'
        AND T1.APPROVE_STATUS = T2.VALUE
        AND T2.STATUS = '1') AS APPROVE_STATUS_DISPLAY
FROM
    HR.LEAVERECORD T1
JOIN
    HR.PROCESS_FLOW T2
ON
    T1.LEAVE_NO = T2.LEAVE_NO
JOIN
    HR.EMPLOYEE T4
ON
    T1.EMP_ID = T4.EMP_ID
JOIN
    BASE.LEAVETYPE T3
ON
    T1.LEAVETYPE_ID = T3.LEAVETYPE_ID
AND T4.EMPTYPE = T3.EMPTYPE
WHERE
    T2.EMP_ID = :empId
AND T2.APPROVE_STATUS = 'sign'
AND (T1.APPROVE_STATUS = 'sign' OR T1.APPROVE_STATUS = 'reject')
ORDER BY
    T1.LEAVE_NO ASC
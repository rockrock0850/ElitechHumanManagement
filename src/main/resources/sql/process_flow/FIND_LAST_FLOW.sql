SELECT
    T1.SEQ
FROM
    HR.PROCESS_FLOW T1
WHERE
    T1.LEAVE_NO = :leaveNo
AND T1.EMP_ID = :empId
AND (
        T1.APPROVE_STATUS = 'sign'
    OR  T1.APPROVE_STATUS = '')
AND T1.SEQ =
    (
        SELECT
            MAX(T2.SEQ)
        FROM
            HR.PROCESS_FLOW T2
        WHERE
            T2.LEAVE_NO = :leaveNo)
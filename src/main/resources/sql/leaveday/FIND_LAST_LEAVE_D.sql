SELECT
    MAX(T1.SEQ) AS seq,
    T1.HOURS
FROM
    HR.LEAVEDAY_D T1
WHERE
    T1.EMP_ID = :empId
AND T1.LEAVE_NO = :leaveNo
AND T1.ACTION = 'U'
AND T1.LEAVETYPE_ID = :leavetypeId
GROUP BY
    T1.HOURS
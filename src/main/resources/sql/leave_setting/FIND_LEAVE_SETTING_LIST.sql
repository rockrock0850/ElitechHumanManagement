SELECT
    T1.LEAVETYPE_ID,
    T2.LEAVE_NAME,
    T2.IS_YEARS,
    T1.EMPTYPE,
    T1.LEAVE_YEAR,
    T1.YEARS,
    T1.LEAVE_DAYS,
    T1.IS_PROPORTIONAL
FROM
    HR.LEAVE_SETTING T1
JOIN
    BASE.LEAVETYPE T2
ON
    T1.LEAVETYPE_ID = T2.LEAVETYPE_ID
AND T1.EMPTYPE = T2.EMPTYPE
${CONDITIONS}
ORDER BY
    T1.SEQ ASC
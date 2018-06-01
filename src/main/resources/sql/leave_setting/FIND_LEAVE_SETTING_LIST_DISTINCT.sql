SELECT DISTINCT
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'emptype'
        AND T2.VALUE = T1.EMPTYPE) AS EMPTYPEDISPLAY,
    T1.LEAVE_YEAR,
    T1.EMPTYPE
FROM
    HR.LEAVE_SETTING T1
${CONDITIONS}
ORDER BY
    T1.LEAVE_YEAR ASC
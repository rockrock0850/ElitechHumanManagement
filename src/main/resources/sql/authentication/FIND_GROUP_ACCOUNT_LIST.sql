SELECT
    T1.ROLE_ID,
    T1.ROLE_NAME,
    T1.STATUS,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'status'
        AND T2.VALUE = T1.STATUS) AS STATUSDISPLAY
FROM
    PERMISSION.USER_ROLE T1
${CONDITIONS}
ORDER BY
    T1.ROLE_ID ASC
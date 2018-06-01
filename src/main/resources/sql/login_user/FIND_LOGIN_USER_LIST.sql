SELECT
    T1.USER_ID,
    T1.USER_NAME,
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
    PERMISSION.LOGIN_USER T1
${CONDITIONS}
ORDER BY
    T1.USER_ID ASC
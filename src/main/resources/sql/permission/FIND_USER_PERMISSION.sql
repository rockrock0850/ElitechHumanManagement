SELECT
    T1.FUNCTION_ID,
    T1.BUTTON_ID
FROM
    PERMISSION.PERMISSION_USER T1
JOIN
    PERMISSION.LOGIN_USER T5
ON
    T1.USER_ID = T5.USER_ID
WHERE
    T1.USER_ID = :userId
AND T5.STATUS = '1'
UNION
SELECT
    T2.FUNCTION_ID,
    T2.BUTTON_ID
FROM
    PERMISSION.PERMISSION_ROLE T2
WHERE
    T2.ROLE_ID IN
    (
        SELECT
            T3.ROLE_ID
        FROM
            PERMISSION.USER_ROLE_MAPPING T3
        JOIN
            PERMISSION.USER_ROLE T4
        ON
            T3.ROLE_ID = T4.ROLE_ID
        WHERE
            T3.USER_ID = :userId
        AND T4.STATUS = '1')
ORDER BY
    FUNCTION_ID ASC
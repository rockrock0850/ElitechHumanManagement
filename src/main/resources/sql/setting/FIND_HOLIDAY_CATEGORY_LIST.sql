SELECT
    T1.LEAVETYPE_ID,
    T1.LEAVE_NAME,
    T1.UNIT,
    T1.EMPTYPE,
    T1.GENDER,
    T1.IS_YEARS,
    T1.STATUS,
    T1.IS_DOCUMENT,
    T1.IS_SUBSTITUTE,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'unit'
        AND T2.VALUE = T1.UNIT) AS UNITDISPLAY,
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
            T2.KEY = 'gender'
        AND T2.VALUE = T1.GENDER) AS GENDERDISPLAY,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'is_years'
        AND T2.VALUE = T1.IS_YEARS) AS ISYEARSDISPLAY,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'status'
        AND T2.VALUE = T1.STATUS) AS STATUSDISPLAY,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'is_document'
        AND T2.VALUE = T1.IS_DOCUMENT) AS ISDOCUMENTDISPLAY,
    (
        SELECT
            T2.DISPLAY
        FROM
            BASE.PARAM T2
        WHERE
            T2.KEY = 'is_substitute'
        AND T2.VALUE = T1.IS_SUBSTITUTE) AS ISSUBSTITUTEDISPLAY,
    T1.EXPIRE,
    T1.DOCUMENT_MSG
FROM
    BASE.LEAVETYPE T1
${CONDITIONS}
ORDER BY
    T1.LEAVETYPE_ID ASC
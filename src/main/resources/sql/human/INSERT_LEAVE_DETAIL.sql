INSERT
INTO
    HR.LEAVEDAY_D
    (
        EMP_ID,
        YEARS,
        TRANS_DATE,
        LEAVETYPE_ID,
        ACTION,
        HOURS,
        CREATE_USER,
        CREATE_TIME
    )
    VALUES
    (
        :empId,
        :years,
        :transDate,
        :leaveTypeId,
        :action,
        :hours,
        :createUser,
        :createTime
    )
set serveroutput on;
     DECLARE 
        V1 VARCHAR2(200); --32767
        F1 UTL_FILE.FILE_TYPE; 
        v_id NUMBER(38) := 1;
     BEGIN 
        F1 := UTL_FILE.FOPEN('USER_DIR','cuvinte.txt','R'); 
        Loop
        BEGIN
    UTL_FILE.GET_LINE(F1,V1); 
    INSERT INTO TAG
    (TAG_ID,
    TAG_NAME
    )
    VALUES
    (v_id,
    V1
    );
     v_id := v_id +1;
     EXCEPTION WHEN No_Data_Found THEN EXIT; END;
        end loop;

        IF UTL_FILE.IS_OPEN(F1) THEN
     dbms_output.put_line('File is Open');
        end if;

        UTL_FILE.FCLOSE(F1); 
     END; 
     /
    set serveroutput off;
    
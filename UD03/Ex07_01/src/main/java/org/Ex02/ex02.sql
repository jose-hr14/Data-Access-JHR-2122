-- FUNCTION: public.ex02(integer)

-- DROP FUNCTION public.ex02(integer);

CREATE OR REPLACE FUNCTION public.ex02(
    thedepartment integer)
    RETURNS SETOF employee
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
DECLARE
    employees employee;
BEGIN
    FOR employees IN
        SELECT * FROM employee
        WHERE deptno = thedepartment
        LOOP
            RETURN NEXT employees;
        END LOOP;
END;
$BODY$;

ALTER FUNCTION public.ex02(integer)
    OWNER TO postgres;

-- FUNCTION: public.ex01(character varying)

-- DROP FUNCTION public.ex01(character varying);

CREATE OR REPLACE FUNCTION public.ex01(
    thejob character varying)
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
        WHERE job = thejob
        LOOP
            RETURN NEXT employees;
        END LOOP;
END;
$BODY$;

ALTER FUNCTION public.ex01(character varying)
    OWNER TO postgres;

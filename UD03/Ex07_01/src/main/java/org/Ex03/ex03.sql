-- FUNCTION: public.ex03(character varying)

-- DROP FUNCTION public.ex03(character varying);

CREATE OR REPLACE FUNCTION public.ex03(
    name character varying)
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
        WHERE UPPER(ename) LIKE UPPER(CONCAT(name, '%'))
        LOOP
            RETURN NEXT employees;
        END LOOP;
END;
$BODY$;

ALTER FUNCTION public.ex03(character varying)
    OWNER TO postgres;

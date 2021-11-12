-- FUNCTION: public.isenrolled(character varying, integer)

-- DROP FUNCTION public.isenrolled(character varying, integer);

CREATE OR REPLACE FUNCTION public.isenrolled(
    studentid character varying,
    coursecode integer)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
    enrollmentcount int;
BEGIN
    select count(*) into enrollmentcount from enrollment where course = coursecode AND student = studentid;
    return enrollmentcount;
END;
$BODY$;

ALTER FUNCTION public.isenrolled(character varying, integer)
    OWNER TO postgres;

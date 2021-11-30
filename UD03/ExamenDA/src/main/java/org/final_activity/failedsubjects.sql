-- FUNCTION: public.failedsubjects(character varying)

-- DROP FUNCTION public.failedsubjects(character varying);

CREATE OR REPLACE FUNCTION public.failedsubjects(
    studentid character varying)
    RETURNS integer
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
DECLARE
    subjectcount int;
BEGIN
    select count(s.score) into subjectcount from enrollment inner join scores s on enrollment.code = s.enrollmentid inner join subjects s2 on s2.code = s.subjectid where student = studentid and s.score < 5;
    return subjectcount;
END;
$BODY$;

ALTER FUNCTION public.failedsubjects(character varying)
    OWNER TO postgres;

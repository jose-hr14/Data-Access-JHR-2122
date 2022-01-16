--
-- PostgreSQL database dump
--

-- Dumped from database version 14.0
-- Dumped by pg_dump version 14.0

-- Started on 2022-01-17 00:29:12

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 855 (class 1247 OID 16737)
-- Name: email; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.email AS character varying
	CONSTRAINT email CHECK (((VALUE)::text ~~ '%@%.com'::text));


ALTER DOMAIN public.email OWNER TO postgres;

--
-- TOC entry 851 (class 1247 OID 16734)
-- Name: phone; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.phone AS character varying
	CONSTRAINT "phone number lenght" CHECK ((length((VALUE)::text) = 9));


ALTER DOMAIN public.phone OWNER TO postgres;

--
-- TOC entry 846 (class 1247 OID 16727)
-- Name: score; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.score AS integer
	CONSTRAINT "bigger than 0" CHECK ((VALUE >= 0))
	CONSTRAINT "lower than 10" CHECK ((VALUE <= 10));


ALTER DOMAIN public.score OWNER TO postgres;

--
-- TOC entry 217 (class 1255 OID 16645)
-- Name: failedsubjects(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.failedsubjects(studentid character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    subjectcount int;
BEGIN
    select count(s.score) into subjectcount from enrollment inner join scores s on enrollment.code = s.enrollmentid inner join subjects s2 on s2.code = s.subjectid where student = studentid and s.score < 5;
    return subjectcount;
END;
$$;


ALTER FUNCTION public.failedsubjects(studentid character varying) OWNER TO postgres;

--
-- TOC entry 218 (class 1255 OID 16646)
-- Name: isenrolled(character varying, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.isenrolled(studentid character varying, coursecode integer) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
    enrollmentcount int;
BEGIN
    select count(*) into enrollmentcount from enrollment where course = coursecode AND student = studentid;
    return enrollmentcount;
END;
$$;


ALTER FUNCTION public.isenrolled(studentid character varying, coursecode integer) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 211 (class 1259 OID 16585)
-- Name: course; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.course (
    code integer NOT NULL,
    name character varying
);


ALTER TABLE public.course OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16584)
-- Name: course_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.course_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.course_code_seq OWNER TO postgres;

--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 210
-- Name: course_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.course_code_seq OWNED BY public.course.code;


--
-- TOC entry 213 (class 1259 OID 16594)
-- Name: enrollment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enrollment (
    code integer NOT NULL,
    student character varying,
    course integer
);


ALTER TABLE public.enrollment OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16593)
-- Name: enrollment_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.enrollment_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.enrollment_code_seq OWNER TO postgres;

--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 212
-- Name: enrollment_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.enrollment_code_seq OWNED BY public.enrollment.code;


--
-- TOC entry 216 (class 1259 OID 16626)
-- Name: scores; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.scores (
    enrollmentid integer NOT NULL,
    subjectid integer NOT NULL,
    score integer
);


ALTER TABLE public.scores OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16577)
-- Name: student; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.student (
    firstname character varying,
    lastname character varying,
    idcard character varying NOT NULL,
    email character varying,
    phone character varying
);


ALTER TABLE public.student OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16613)
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subjects (
    code integer NOT NULL,
    name character varying,
    year integer,
    courseid integer,
    hours integer
);


ALTER TABLE public.subjects OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16612)
-- Name: subjects_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subjects_code_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subjects_code_seq OWNER TO postgres;

--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 214
-- Name: subjects_code_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subjects_code_seq OWNED BY public.subjects.code;


--
-- TOC entry 3197 (class 2604 OID 16597)
-- Name: enrollment code; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment ALTER COLUMN code SET DEFAULT nextval('public.enrollment_code_seq'::regclass);


--
-- TOC entry 3356 (class 0 OID 16585)
-- Dependencies: 211
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.course (code, name) VALUES (1, 'DAM');
INSERT INTO public.course (code, name) VALUES (2, 'DAW');
INSERT INTO public.course (code, name) VALUES (102, 'Graphic design');
INSERT INTO public.course (code, name) VALUES (4, 'Economics');


--
-- TOC entry 3358 (class 0 OID 16594)
-- Dependencies: 213
-- Data for Name: enrollment; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.enrollment (code, student, course) VALUES (15, '1', 1);
INSERT INTO public.enrollment (code, student, course) VALUES (16, '3', 2);
INSERT INTO public.enrollment (code, student, course) VALUES (18, '6', 2);
INSERT INTO public.enrollment (code, student, course) VALUES (19, '100', 1);
INSERT INTO public.enrollment (code, student, course) VALUES (20, '22', 1);
INSERT INTO public.enrollment (code, student, course) VALUES (21, 'idpanita', 1);


--
-- TOC entry 3361 (class 0 OID 16626)
-- Dependencies: 216
-- Data for Name: scores; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (21, 1, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (21, 2, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (21, 3, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (21, 4, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (15, 1, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (15, 2, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (15, 3, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (15, 4, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (16, 5, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (18, 5, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (19, 1, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (19, 2, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (19, 3, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (19, 4, 0);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (20, 3, 6);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (20, 4, 5);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (20, 2, 6);
INSERT INTO public.scores (enrollmentid, subjectid, score) VALUES (20, 1, 6);


--
-- TOC entry 3354 (class 0 OID 16577)
-- Dependencies: 209
-- Data for Name: student; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('Jose', 'Hernandez', '1', 'a', '123');
INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('David', 'Ripoll', '22', 'david@bobo.com', '123456789');
INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('Deivid', 'Del Arco', '12321', 'delarco@marenostrum.com', '123456789');
INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('Deivid', 'Del Arco', '3', '', '');
INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('Laura', 'Blanco', '6', '', '');
INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('Jose', 'García', '100', 'jrgarcia@iesmarenostrum.com', '616116611');
INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('Antonio', 'Luque', '101', NULL, NULL);
INSERT INTO public.student (firstname, lastname, idcard, email, phone) VALUES ('Pablo', 'Alcón', 'idpanita', '', '');


--
-- TOC entry 3360 (class 0 OID 16613)
-- Dependencies: 215
-- Data for Name: subjects; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (1, 'Development Environments', 1, 1, 1);
INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (2, 'Database Management Systems', 1, 1, 1);
INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (3, 'Services and processes', 1, 1, 1);
INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (4, 'Data Access', 1, 1, 1);
INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (5, 'Javascript', 2, 2, 2);
INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (30, 'Introduction to CAD/CAM', 60, 102, 1);
INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (40, 'Accounting', 90, 4, 1);
INSERT INTO public.subjects (code, name, year, courseid, hours) VALUES (6, 'Web development in client environment', 2, 2, 2);


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 210
-- Name: course_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_code_seq', 10, true);


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 212
-- Name: enrollment_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.enrollment_code_seq', 21, true);


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 214
-- Name: subjects_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.subjects_code_seq', 5, true);


--
-- TOC entry 3203 (class 2606 OID 16592)
-- Name: course course_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_pkey PRIMARY KEY (code);


--
-- TOC entry 3205 (class 2606 OID 16601)
-- Name: enrollment enrollment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT enrollment_pkey PRIMARY KEY (code);


--
-- TOC entry 3198 (class 2606 OID 16731)
-- Name: scores score0; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.scores
    ADD CONSTRAINT score0 CHECK ((score >= 0)) NOT VALID;


--
-- TOC entry 3199 (class 2606 OID 16732)
-- Name: scores score10; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.scores
    ADD CONSTRAINT score10 CHECK ((score <= 10)) NOT VALID;


--
-- TOC entry 3209 (class 2606 OID 16630)
-- Name: scores scores_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT scores_pkey PRIMARY KEY (enrollmentid, subjectid);


--
-- TOC entry 3201 (class 2606 OID 16583)
-- Name: student student_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.student
    ADD CONSTRAINT student_pkey PRIMARY KEY (idcard);


--
-- TOC entry 3207 (class 2606 OID 16620)
-- Name: subjects subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (code);


--
-- TOC entry 3211 (class 2606 OID 16607)
-- Name: enrollment fk_course; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT fk_course FOREIGN KEY (course) REFERENCES public.course(code);


--
-- TOC entry 3212 (class 2606 OID 16621)
-- Name: subjects fk_courseid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT fk_courseid FOREIGN KEY (courseid) REFERENCES public.course(code);


--
-- TOC entry 3213 (class 2606 OID 16631)
-- Name: scores fk_enrollmentid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT fk_enrollmentid FOREIGN KEY (enrollmentid) REFERENCES public.enrollment(code);


--
-- TOC entry 3210 (class 2606 OID 16602)
-- Name: enrollment fk_student; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enrollment
    ADD CONSTRAINT fk_student FOREIGN KEY (student) REFERENCES public.student(idcard);


--
-- TOC entry 3214 (class 2606 OID 16636)
-- Name: scores fk_subjectid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.scores
    ADD CONSTRAINT fk_subjectid FOREIGN KEY (subjectid) REFERENCES public.subjects(code);


-- Completed on 2022-01-17 00:29:12

--
-- PostgreSQL database dump complete
--
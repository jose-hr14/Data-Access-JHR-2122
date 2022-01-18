--Enumerated type for the employees' job
--Domain to check that the ename is always in capital letters from 
--employee table. The same domain applies to dname and loc from dept 
--table.

--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

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
-- Name: CapitalLetters; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public."CapitalLetters" AS character varying
	CONSTRAINT capitalletters CHECK (((VALUE)::text = upper((VALUE)::text)));


ALTER DOMAIN public."CapitalLetters" OWNER TO postgres;

--
-- Name: jobs; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.jobs AS ENUM (
    'ANALYST',
    'CLERK',
    'MANAGER',
    'PRESIDENT',
    'SALESMAN'
);


ALTER TYPE public.jobs OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: dept; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.dept (
    deptno integer NOT NULL,
    dname public."CapitalLetters",
    loc public."CapitalLetters"
);


ALTER TABLE public.dept OWNER TO postgres;

--
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee (
    empno integer NOT NULL,
    ename public."CapitalLetters",
    job public.jobs,
    depno integer
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- Data for Name: dept; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.dept (deptno, dname, loc) VALUES (10, 'ACCOUNTING', 'NEW YORK');
INSERT INTO public.dept (deptno, dname, loc) VALUES (20, 'RESEARCH', 'DALLAS');
INSERT INTO public.dept (deptno, dname, loc) VALUES (30, 'SALES', 'CHICAGO');
INSERT INTO public.dept (deptno, dname, loc) VALUES (40, 'OPERATIONS', 'BOSTON');


--
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.employee (empno, ename, job, depno) VALUES (7369, 'SMITH', 'CLERK', 20);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7499, 'ALLEN', 'SALESMAN', 30);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7521, 'WARD', 'SALESMAN', 30);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7566, 'JONES', 'MANAGER', 20);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7654, 'MARTIN', 'SALESMAN', 30);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7698, 'BLAKE', 'MANAGER', 30);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7782, 'CLARK', 'MANAGER', 10);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7788, 'SCOTT', 'ANALYST', 20);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7839, 'KING', 'PRESIDENT', 10);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7844, 'TURNER', 'SALESMAN', 30);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7876, 'ADAMS', 'CLERK', 20);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7900, 'JAMES', 'CLERK', 30);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7902, 'FORD', 'ANALYST', 20);
INSERT INTO public.employee (empno, ename, job, depno) VALUES (7934, 'MILLER', 'CLERK', 10);


--
-- Name: dept dept_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.dept
    ADD CONSTRAINT dept_pkey PRIMARY KEY (deptno);


--
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (empno);


--
-- PostgreSQL database dump complete
--

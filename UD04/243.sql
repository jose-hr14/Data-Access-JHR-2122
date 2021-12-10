--
-- PostgreSQL database dump
--

-- Dumped from database version 13.4
-- Dumped by pg_dump version 13.4

-- Started on 2021-12-10 19:53:57

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

DROP DATABASE "2.4.3";
--
-- TOC entry 3041 (class 1262 OID 16747)
-- Name: 2.4.3; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "2.4.3" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Spain.1252';


ALTER DATABASE "2.4.3" OWNER TO postgres;

\connect "2.4.3"

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
-- TOC entry 663 (class 1247 OID 16897)
-- Name: authorcode; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.authorcode AS character varying
	CONSTRAINT regex CHECK (((VALUE)::text ~~ '[A-Z]{1,3}[0-9]{1,4}'::text));


ALTER DOMAIN public.authorcode OWNER TO postgres;

--
-- TOC entry 648 (class 1247 OID 16825)
-- Name: dimenssionsdomain; Type: DOMAIN; Schema: public; Owner: postgres
--

CREATE DOMAIN public.dimenssionsdomain AS double precision
	CONSTRAINT onlypositive CHECK ((VALUE > (0)::double precision));


ALTER DOMAIN public.dimenssionsdomain OWNER TO postgres;

--
-- TOC entry 645 (class 1247 OID 16789)
-- Name: dimenssionstype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.dimenssionstype AS (
	width public.dimenssionsdomain,
	height public.dimenssionsdomain
);


ALTER TYPE public.dimenssionstype OWNER TO postgres;

--
-- TOC entry 642 (class 1247 OID 16780)
-- Name: materialtype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.materialtype AS ENUM (
    'iron',
    'bronze',
    'marble'
);


ALTER TYPE public.materialtype OWNER TO postgres;

--
-- TOC entry 636 (class 1247 OID 16765)
-- Name: paintingtypes; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.paintingtypes AS ENUM (
    'oil painting',
    'water colour',
    'pastel'
);


ALTER TYPE public.paintingtypes OWNER TO postgres;

--
-- TOC entry 639 (class 1247 OID 16772)
-- Name: styles; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.styles AS ENUM (
    'greco roman',
    'neo classic',
    'cubism'
);


ALTER TYPE public.styles OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 201 (class 1259 OID 16756)
-- Name: artwork; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artwork (
    title character varying,
    dated date,
    style public.styles,
    authorcode character varying,
    code integer NOT NULL
);


ALTER TABLE public.artwork OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16845)
-- Name: artwork_code_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.artwork ALTER COLUMN code ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.artwork_code_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 203 (class 1259 OID 16841)
-- Name: artworkcode; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.artworkcode
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.artworkcode OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16748)
-- Name: author; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.author (
    code character varying NOT NULL,
    name character varying,
    nationality character varying
);


ALTER TABLE public.author OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16868)
-- Name: painting; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.painting (
    type public.paintingtypes,
    dimensions public.dimenssionstype
)
INHERITS (public.artwork);


ALTER TABLE public.painting OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16855)
-- Name: sculpture; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sculpture (
    material public.materialtype,
    weight public.dimenssionsdomain
)
INHERITS (public.artwork);


ALTER TABLE public.sculpture OWNER TO postgres;

--
-- TOC entry 3031 (class 0 OID 16756)
-- Dependencies: 201
-- Data for Name: artwork; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3030 (class 0 OID 16748)
-- Dependencies: 200
-- Data for Name: author; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3035 (class 0 OID 16868)
-- Dependencies: 206
-- Data for Name: painting; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3034 (class 0 OID 16855)
-- Dependencies: 205
-- Data for Name: sculpture; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3042 (class 0 OID 0)
-- Dependencies: 204
-- Name: artwork_code_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.artwork_code_seq', 1, false);


--
-- TOC entry 3043 (class 0 OID 0)
-- Dependencies: 203
-- Name: artworkcode; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.artworkcode', 1, false);


--
-- TOC entry 2890 (class 2606 OID 16755)
-- Name: author author_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.author
    ADD CONSTRAINT author_pkey PRIMARY KEY (code);


--
-- TOC entry 2892 (class 2606 OID 16854)
-- Name: artwork code; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artwork
    ADD CONSTRAINT code PRIMARY KEY (code);


--
-- TOC entry 2896 (class 2606 OID 16875)
-- Name: painting painting_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.painting
    ADD CONSTRAINT painting_pkey PRIMARY KEY (code) INCLUDE (code);


--
-- TOC entry 2894 (class 2606 OID 16862)
-- Name: sculpture sculpture_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sculpture
    ADD CONSTRAINT sculpture_pkey PRIMARY KEY (code);


--
-- TOC entry 2897 (class 2606 OID 16798)
-- Name: artwork fk_artwork; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artwork
    ADD CONSTRAINT fk_artwork FOREIGN KEY (authorcode) REFERENCES public.author(code) NOT VALID;


--
-- TOC entry 2899 (class 2606 OID 16876)
-- Name: painting painting_authorcode_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.painting
    ADD CONSTRAINT painting_authorcode_fkey FOREIGN KEY (authorcode) REFERENCES public.author(code);


--
-- TOC entry 2898 (class 2606 OID 16863)
-- Name: sculpture sculpture_authorcode_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sculpture
    ADD CONSTRAINT sculpture_authorcode_fkey FOREIGN KEY (authorcode) REFERENCES public.author(code);


-- Completed on 2021-12-10 19:53:57

--
-- PostgreSQL database dump complete
--


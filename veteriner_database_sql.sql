--
-- PostgreSQL database dump
--

-- Dumped from database version 17.3
-- Dumped by pg_dump version 17.3

-- Started on 2025-06-19 12:35:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4934 (class 0 OID 17658)
-- Dependencies: 218
-- Data for Name: animals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.animals (date_of_birth, created_at, customer_id, id, updated_at, breed, colour, gender, name, species) FROM stdin;
2023-05-20	2025-06-19 00:12:45.099237	1	1	\N	Tekir	Beyaz	Erkek	Ponçik	Kedi
2022-11-12	2025-06-19 00:13:04.971348	1	2	\N	Golden	Sarı	Dişi	Boncuk	Köpek
2021-08-01	2025-06-19 00:13:12.069495	1	3	\N	Van	Siyah	Erkek	Zeytin	Kedi
2024-01-03	2025-06-19 00:13:21.782672	1	4	\N	Muhabbet	Yeşil	Dişi	Fıstık	Kuş
2022-06-15	2025-06-19 00:13:27.955277	1	5	\N	British Shorthair	Gri	Dişi	Pamuk	Kedi
2020-12-22	2025-06-19 00:13:34.192772	2	6	\N	Doberman	Siyah	Erkek	Kara	Köpek
2023-04-14	2025-06-19 00:13:40.123319	2	7	\N	Ankara	Beyaz	Dişi	Maviş	Kedi
2021-03-03	2025-06-19 00:13:44.854337	2	8	\N	Pug	Bej	Dişi	Kırpık	Köpek
2024-02-02	2025-06-19 00:13:50.570509	2	9	\N	Kanarya	Sarı	Erkek	Limon	Kuş
2022-10-10	2025-06-19 00:13:56.308583	2	10	\N	Tekir	Kahverengi	Dişi	Şeker	Kedi
2023-03-10	2025-06-19 00:14:02.327438	3	11	\N	Tekir	Gri	Dişi	Minnoş	Kedi
2021-07-07	2025-06-19 00:14:08.258315	3	12	\N	Golden	Krem	Erkek	Tarçın	Köpek
2022-02-20	2025-06-19 00:14:13.97741	3	13	\N	Sarman	Turuncu	Erkek	Gofret	Kedi
2024-04-01	2025-06-19 00:14:20.093754	3	14	\N	Papağan	Yeşil	Dişi	Zuzu	Kuş
2023-09-09	2025-06-19 00:14:25.046563	3	15	\N	Terrier	Beyaz	Erkek	Köpük	Köpek
2020-05-15	2025-06-19 00:14:31.165627	4	16	\N	Van	Beyaz	Erkek	Bulut	Kedi
2021-06-18	2025-06-19 00:14:37.885179	4	17	\N	Beagle	Kahverengi	Dişi	Çakıl	Köpek
2022-02-22	2025-06-19 00:14:45.375386	5	18	\N	Shih Tzu	Beyaz	Dişi	Çiko	Köpek
2023-06-06	2025-06-19 00:14:51.847739	6	19	\N	Tekir	Kahverengi	Erkek	Mars	Kedi
2020-09-09	2025-06-19 00:14:57.705523	7	20	\N	Kangal	Kırçıl	Erkek	Atlas	Köpek
\.


--
-- TOC entry 4936 (class 0 OID 17666)
-- Dependencies: 220
-- Data for Name: appointments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.appointments (animal_id, appointment_date, doctor_id, id) FROM stdin;
1	2029-10-02 09:00:00	2	1
2	2029-10-03 10:00:00	3	2
5	2029-10-07 13:00:00	7	5
6	2029-10-08 14:00:00	8	6
7	2029-10-09 15:00:00	9	7
8	2029-10-10 16:00:00	10	8
10	2029-10-05 18:00:00	5	9
10	2029-10-05 13:00:00	5	4
\.


--
-- TOC entry 4938 (class 0 OID 17672)
-- Dependencies: 222
-- Data for Name: available_dates; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.available_dates (available, doctor_id, id) FROM stdin;
2029-10-02	2	2
2029-10-03	3	3
2029-10-05	5	5
2029-10-06	6	6
2029-10-07	7	7
2029-10-08	8	8
2029-10-09	9	9
2029-10-10	10	10
2022-10-12	5	1
\.


--
-- TOC entry 4940 (class 0 OID 17678)
-- Dependencies: 224
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (created_at, id, updated_at, address, city, email, name, phone) FROM stdin;
2025-06-18 23:56:33.025706	1	\N	Esenyurt	İstanbul	mehmetkerem@gmail.com	Mehmet Kerem	0555 555 55 55
2025-06-18 23:57:00.730731	2	\N	Bayrampaşa	İstanbul	emreomur@gmail.com	Emre Ömür	0555 444 55 55
2025-06-18 23:57:18.463708	3	\N	Beylikdüzü	İstanbul	enesduman@gmail.com	Enes Duman	0555 333 55 55
2025-06-18 23:57:38.703408	4	\N	Mamak	Ankara	patika@gmail.com	Patika	0555 423 55 55
2025-06-18 23:58:34.109699	6	\N	Silivri	İstanbul	murat@gmail.com	Murat	0554 424 55 55
2025-06-18 23:58:46.616102	7	\N	Silivri	İstanbul	ali@gmail.com	Ali	0554 427 55 55
2025-06-18 23:58:55.832088	8	\N	Silivri	İstanbul	Veli@gmail.com	Veli	0554 421 55 55
2025-06-18 23:59:10.352101	9	\N	Silivri	İstanbul	test@gmail.com	Test	0554 421 53 55
2025-06-18 23:59:36.513967	10	\N	Merkez	Kırklareli	umut@gmail.com	Umut	0554 421 23 55
2025-06-18 23:58:14.427618	5	\N	Merkez	Ordu	fatmagül2@gmail.com	Fatmagül	0515 424 55 55
\.


--
-- TOC entry 4942 (class 0 OID 17688)
-- Dependencies: 226
-- Data for Name: doctors; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctors (created_at, id, updated_at, address, city, email, name, phone) FROM stdin;
2025-06-19 00:23:14.943977	1	\N	Edirne Merkez	Edirne	doktorcivanim1@gmail.com	Doktor Civanım	053435034333
2025-06-19 00:23:22.674265	2	\N	Keşan Mahallesi 5. Sokak	Keşan	doktorayse@gmail.com	Doktor Ayşe	054212345678
2025-06-19 00:23:31.900617	3	\N	Uzunköprü Caddesi No:12	Uzunköprü	doktormehmet@gmail.com	Doktor Mehmet	055576543210
2025-06-19 00:23:54.108323	5	\N	Meriç Caddesi No:8	Meriç	doktorali@gmail.com	Doktor Ali	054487654321
2025-06-19 00:23:58.908446	6	\N	Süloğlu Mahallesi 1. Sokak	Süloğlu	doktorselin@gmail.com	Doktor Selin	055512345678
2025-06-19 00:24:03.852562	7	\N	İpsala Caddesi No:15	İpsala	doktorkan@gmail.com	Doktor Can	053423456789
2025-06-19 00:24:08.421942	8	\N	Lalapaşa Mahallesi 7. Sokak	Lalapaşa	doktoremine@gmail.com	Doktor Emine	054534567890
2025-06-19 00:24:14.447358	9	\N	Enez Caddesi No:20	Enez	doktorhasan@gmail.com	Doktor Hasan	055598765432
2025-06-19 00:24:19.942365	10	2025-06-19 00:32:19.245149	Keşan Mahallesi 9. Sokak	Keşan	test@gmail.com	Doktor Zeynep	053467890123
\.


--
-- TOC entry 4944 (class 0 OID 17698)
-- Dependencies: 228
-- Data for Name: vaccines; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vaccines (protection_finish_date, protection_start_date, animal_id, created_at, id, updated_at, code, name) FROM stdin;
2022-01-15	2021-01-15	2	2025-06-19 00:49:04.145807	4	\N	101	Kuduz
2023-03-10	2022-03-10	3	2025-06-19 00:49:09.287236	5	\N	302	Corona
2020-07-20	2019-07-20	4	2025-06-19 00:49:17.077272	6	\N	404	Mikroplasma
2024-11-11	2023-11-11	5	2025-06-19 00:49:23.287584	7	\N	555	Gençlik
2021-04-04	2020-04-04	6	2025-06-19 00:49:29.349034	8	\N	666	İç Parazit
2021-04-04	2020-04-04	6	2025-06-19 00:49:44.302982	9	\N	666	İç Parazit
2021-04-04	2020-04-04	6	2025-06-19 00:49:52.76312	10	\N	666	İç Parazit
2023-08-08	2022-08-08	7	2025-06-19 00:50:44.553817	12	\N	777	Dış Parazit
2022-05-05	2021-05-05	8	2025-06-19 00:50:51.109344	13	\N	888	Bronchine
2019-02-02	2018-02-02	9	2025-06-19 00:50:56.867439	14	\N	999	Leptospira
2024-01-01	2023-01-01	10	2025-06-19 00:51:06.4525	15	\N	103	Hepatit
2024-03-04	2020-05-04	6	2025-06-19 00:50:30.731075	11	2025-06-19 01:13:40.787292	666	İç Parazit
2030-06-06	2020-06-06	1	2025-06-19 00:44:19.214205	1	2025-06-19 01:14:35.086759	207	PT
2024-06-06	2022-07-06	1	2025-06-19 12:10:36.425588	17	\N	209	HX
\.


--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 217
-- Name: animals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.animals_id_seq', 22, true);


--
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 219
-- Name: appointments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.appointments_id_seq', 9, true);


--
-- TOC entry 4952 (class 0 OID 0)
-- Dependencies: 221
-- Name: available_dates_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.available_dates_id_seq', 10, true);


--
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 223
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customers_id_seq', 10, true);


--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 225
-- Name: doctors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.doctors_id_seq', 10, true);


--
-- TOC entry 4955 (class 0 OID 0)
-- Dependencies: 227
-- Name: vaccines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vaccines_id_seq', 17, true);


-- Completed on 2025-06-19 12:35:14

--
-- PostgreSQL database dump complete
--


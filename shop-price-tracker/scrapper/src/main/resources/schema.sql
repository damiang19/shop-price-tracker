CREATE SEQUENCE IF NOT EXISTS public.shop_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1;

CREATE TABLE IF NOT EXISTS public.shop
(
    id bigint NOT NULL DEFAULT nextval('shop_id_seq'::regclass),
    product_name_html_class character varying(100) COLLATE pg_catalog."default" NOT NULL,
    price_html_class character varying(100) COLLATE pg_catalog."default" NOT NULL,
    shop_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    shop_url character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT shop_pkey PRIMARY KEY (id)
);
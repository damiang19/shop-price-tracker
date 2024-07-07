CREATE SEQUENCE IF NOT EXISTS public.shop_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1

CREATE SEQUENCE IF NOT EXISTS public.tracked_product_archive_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1

CREATE SEQUENCE IF NOT EXISTS public.tracked_product_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1


CREATE TABLE IF NOT EXISTS public.shop
(
    id bigint NOT NULL DEFAULT nextval('shop_id_seq'::regclass),
    product_name_html_class character varying(100) COLLATE pg_catalog."default" NOT NULL,
    price_html_class character varying(100) COLLATE pg_catalog."default" NOT NULL,
    shop_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    shop_url character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT sklep_pkey PRIMARY KEY (id)
)

CREATE TABLE IF NOT EXISTS public.tracked_product
(
    tracked_product_id bigint NOT NULL DEFAULT nextval('tracked_product_id_seq'::regclass),
    product_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    price numeric NOT NULL,
    product_url character varying(100) COLLATE pg_catalog."default" NOT NULL,
    shop_id bigint NOT NULL,
    created timestamp without time zone,
    CONSTRAINT tracked_product_pkey PRIMARY KEY (tracked_product_id),
    CONSTRAINT fk_shop FOREIGN KEY (shop_id)
        REFERENCES public.shop (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS public.tracked_product_archive
(
    tracked_product_archive_id bigint NOT NULL DEFAULT nextval('tracked_product_archive_id_seq'::regclass),
    price numeric NOT NULL,
    created timestamp without time zone,
    tracked_product_id bigint NOT NULL,
    CONSTRAINT tracked_product_archive_pkey PRIMARY KEY (tracked_product_archive_id),
    CONSTRAINT fk_tracked_product FOREIGN KEY (tracked_product_id)
        REFERENCES public.tracked_product (tracked_product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

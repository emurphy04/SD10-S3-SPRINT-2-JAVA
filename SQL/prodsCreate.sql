-- Table: public.products

-- DROP TABLE IF EXISTS public.products;

CREATE TABLE IF NOT EXISTS public.products
(
    itemname text COLLATE pg_catalog."default",
    itemsku text COLLATE pg_catalog."default",
    itemprice double precision,
    itemdesc text COLLATE pg_catalog."default",
    itemcat text COLLATE pg_catalog."default",
    "userListed" text COLLATE pg_catalog."default"
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.products
    OWNER to postgres;
DROP TABLE IF EXISTS public.saves;
CREATE TABLE public.saves (
    id serial NOT NULL PRIMARY KEY,
    name text NOT NULL,
    json text NOT NULL );
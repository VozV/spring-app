--Category table
create table if not exists category
(
	id serial not null
		constraint category_pk
			primary key,
	name varchar
);

alter table category owner to postgres;

create unique index if not exists category_uindex
	on category (id);

INSERT INTO public.category (id, name) VALUES (1, 'Book');
INSERT INTO public.category (id, name) VALUES (3, 'Food');
INSERT INTO public.category (id, name) VALUES (2, 'Magazine');
INSERT INTO public.category (id, name) VALUES (4, 'Tools');

--Product table
create table if not exists product
(
	id serial not null
		constraint product_pk
			primary key,
	category integer not null
		constraint product_category_fk
			references category,
	name varchar
);

alter table product owner to postgres;

create unique index if not exists product_id_uindex
	on product (id);

INSERT INTO public.product (id, category, name) VALUES (4, 3, 'Apple');
INSERT INTO public.product (id, category, name) VALUES (3, 2, 'Times');
INSERT INTO public.product (id, category, name) VALUES (2, 1, 'Three dogs');
INSERT INTO public.product (id, category, name) VALUES (5, 2, 'Murzilka');
INSERT INTO public.product (id, category, name) VALUES (1, 1, 'War and Peace');




DROP TABLE if exists product;
DROP TABLE if exists category;
DROP SEQUENCE IF EXISTS category_id_seq;
DROP SEQUENCE IF EXISTS product_id_seq;

create sequence category_id_seq;
create sequence product_id_seq;
--Category table
create table category
(
	id serial not null
		constraint category_pk
			primary key,
	name varchar
);
create unique index if not exists category_uindex
	on category (id);

--Product table
create table product
(
	id serial not null
		constraint product_pk
			primary key,
	category integer not null
		constraint product_category_fk
			references category,
	name varchar
);
create unique index if not exists product_id_uindex
	on product (id);



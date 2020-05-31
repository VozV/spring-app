DROP TABLE if exists product;
DROP TABLE if exists category;

--Category table
create table category
(
	id serial not null
		constraint category_pk
			primary key,
	name varchar
);
create unique index if not exists category_id_uindex
	on category (id);

--Product table
create table product
(
	id serial not null
		constraint product_pk
			primary key,
	category_id integer not null
		constraint product_category_id_fk
			references category,
	name varchar
);
create unique index if not exists product_id_uindex
	on product (id);



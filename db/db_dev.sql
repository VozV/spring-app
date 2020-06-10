--Category table
create table if not exists category
(
	id serial not null
		constraint category_pk
			primary key,
	name varchar
);

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

INSERT INTO public.product (id, category, name) VALUES (4, 3, 'Apple');
INSERT INTO public.product (id, category, name) VALUES (3, 2, 'Times');
INSERT INTO public.product (id, category, name) VALUES (2, 1, 'Three dogs');
INSERT INTO public.product (id, category, name) VALUES (5, 2, 'Murzilka');
INSERT INTO public.product (id, category, name) VALUES (1, 1, 'War and Peace');

--User table
create table users
(
    id       serial  not null
        constraint users_pk
            primary key,
    name     varchar not null,
    password varchar
);

create unique index users_name_uindex
    on users (name);

-- Role table
create table roles
(
    id   serial  not null
        constraint role_pk
            primary key,
    name varchar not null
);

-- User-role many-to-many
create table users_roles
(
    users_id integer
        constraint table_name_users_id_fkey
            references users,
    roles_id integer
        constraint table_name_role_id_fkey
            references roles
);




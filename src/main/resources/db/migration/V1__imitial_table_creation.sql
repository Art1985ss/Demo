create table if not exists addresses
(
    id               bigint primary key auto_increment,
    country          varchar(255),
    city             varchar(255),
    street           varchar(255),
    house_number     integer,
    apartment_number integer
);

create table if not exists users
(
    id         bigint primary key auto_increment,
    username   varchar(255),
    password   varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    age        integer,
    address_id bigint,
    constraint fk_address_id foreign key (address_id)
        references addresses (id)
        on delete cascade
);

create table if not exists user_authorities
(
    user_id   bigint primary key,
    authority integer,
    constraint fk_user_authority
        foreign key (user_id)
            references users (id)
            on delete cascade
);

create table if not exists products
(
    id             bigint primary key auto_increment,
    name           varchar(255),
    manufacturer   varchar(255),
    price_per_unit decimal(19, 2),
    discount       decimal(19, 2)
);

create table if not exists electronics
(
    energy_consumption integer,
    id                 bigint,
    constraint fk_electronics_product_id
        foreign key (id)
            references products (id)
            on delete cascade
);

create table if not exists food
(
    expiration_date date,
    id              bigint,
    constraint fk_food_product_id
        foreign key (id)
            references products (id)
            on delete cascade
);

create table if not exists orders
(
    id      bigint primary key auto_increment,
    user_id bigint,
    constraint fk_user_id
        foreign key (user_id)
            references users (id)
            on delete cascade
);

create table if not exists orders_products
(
    order_id   bigint,
    product_id bigint,
    amount     decimal(19, 2),
    constraint fk_order_product_id
        foreign key (product_id)
            references products (id)
            on delete cascade,
    constraint fk_product_order_id
        foreign key (order_id)
            references orders (id)
            on delete cascade
);
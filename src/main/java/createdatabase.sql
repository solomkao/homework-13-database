create table airport
(
    id   serial primary key,
    name varchar    not null unique,
    code varchar(3) not null unique
);

create table plane
(
    id            serial primary key,
    model         varchar not null,
    serial_number integer not null unique,
    seats         integer not null,
    airportId     integer references airport (id)
);

create table pilot
(
    id   serial primary key,
    name varchar not null,
    age  integer not null CHECK (age > 25),
    airportId integer references airport (id)
);

create table planes_pilots
(
    id    serial primary key,
    pilot integer references pilot (id),
    plane integer references plane (id),
    unique (pilot, plane)
);
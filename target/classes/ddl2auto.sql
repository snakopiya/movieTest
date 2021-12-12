create table if not exists Cartoon (
    id numeric primary key,
    name varchar(255) not null unique,
    release numeric,
    ageRestriction int,
    typeMovie varchar(255) not null
);

create table if not exists Film (
    id numeric primary key,
    name varchar(255) not null unique,
    release numeric,
    ageRestriction int,
    typeMovie varchar(255) not null
);

create table if not exists Poster (
    id numeric primary key,
    movie int not null,
    typeMovie varchar(255) not null,
    showTime numeric unique,
    numberOfSeats int
);

create table if not exists Ticket (
    id numeric primary key,
    seance int not null,
    age int,
    name varchar(255),
    discount int(1),
    foreign key (seance) references Poster(id) on delete cascade
);
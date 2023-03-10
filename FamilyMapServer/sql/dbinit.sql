DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS AuthToken;

CREATE TABLE User (
    username nvarchar(50),
    password nvarchar(50),
    email nvarchar(50),
    firstName nvarchar(50),
    lastName nvarchar(50),
    gender nvarchar(1),
    personID nvarchar(50),
    PRIMARY KEY(username),
);

CREATE TABLE Person (
    personID nvarchar(50),
    associatedUsername nvarchar(50),
    firstName nvarchar(50),
    lastName nvarchar(50),
    gender nvarchar(1),
    fatherID nvarchar(50),
    motherID nvarchar(50),
    spouseID nvarchar(50),
    PRIMARY KEY(personID),  
);

CREATE TABLE Event (
    eventID nvarchar(50),
    associatedUsername nvarchar(50),
    personID nvarchar(50),
    latitude float,
    longitude float,
    country nvarchar(50),
    city nvarchar(50),
    eventType nvarchar(50),
    year int,
    PRIMARY KEY(eventID),
);

CREATE TABLE AuthToken (
    authToken nvarchar(50),
    username nvarchar(50),
    PRIMARY KEY(authToken),
);
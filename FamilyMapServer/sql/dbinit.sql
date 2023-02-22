DROP IF EXISTS User
DROP IF EXISTS Person
DROP IF EXISTS Event
DROP IF EXISTS AuthToken

CREATE TABLE User (
    username nvarchar(50),
    password nvarchar(50),
    email nvarchar(50),
    firstName nvarchar(50),
    lastName nvarchar(50),
    gender nvarchar(1),
    personID nvarchar(50)
)

CREATE TABLE Person (
    personID nvarchar(50),
    associatedUsername nvarchar(50),
    firstName nvarchar(50),
    lastName nvarchar(50),
    gender nvarchar(1),
    fatherID nvarchar(50),
    motherID nvarchar(50),
    spouseID nvarchar(50)    
)

CREATE TABLE Event (
    eventID nvarchar(50),
    associatedUsername nvarchar(50),
    personID nvarchar(50),
    latitude float,
    longitude float,
    country nvarchar(50),
    city nvarchar(50),
    eventType nvarchar(50),
    year int
)

CREATE TABLE AuthToken (
    authToken nvarchar(50),
    username nvarchar(50)
)
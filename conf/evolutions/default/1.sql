# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "books" ("id" SERIAL NOT NULL PRIMARY KEY,"isbn" VARCHAR(254) NOT NULL,"author" VARCHAR(254) NOT NULL,"title" VARCHAR(254) NOT NULL,"description" VARCHAR(254),"coverPicture" VARCHAR(254),"genre" VARCHAR(254) NOT NULL,"releaseDate" DATE NOT NULL);
create table "users" ("id" SERIAL NOT NULL PRIMARY KEY,"email" VARCHAR(254) NOT NULL,"verifiedEmail" BOOLEAN NOT NULL,"name" VARCHAR(254) NOT NULL,"picture" VARCHAR(254),"gender" VARCHAR(254) NOT NULL,"birthday" VARCHAR(254));

# --- !Downs

drop table "books";
drop table "users";


# Invoices API

## Description

Simple invoice management RESTful API

Front end: [repo](https://github.com/agent-indigo/invoices-web)

## User Roles

- Root: Can do everything but delete itself and reset its password without providing its current one

- User: Can only perform CRUD ops on invoices

## Setup

- Create a new MongoDB database.

- Copy `application.properties.example` to `src/main/resources/application.properties` and fill in the values.

- When running the front end for the first time, you will be prompted to create a password for `root`.

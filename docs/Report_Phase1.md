# Phase 1 

## Introduction

This document contains the relevant design and implementation aspects of LS project's first phase.

## Modeling the database

### Conceptual model ###

The following diagram holds the Entity-Relationship model for the information managed by the system.

(_include an image or a link to the conceptual diagram_)

We highlight the following aspects:

* (_include a list of relevant design issues_)

The conceptual model has the following restrictions:

* (_include a list of relevant design issues_)
    
### Physical Model ###

The physical model of the database is available in (_link to the SQL script with the schema definition_).

We highlight the following aspects of this model:

* (_include a list of relevant design issues_)

## Software organization

### Open-API Specification ###

[Open-API Specification](task-management-api-spec.yaml)

In our Open-API specification, we highlight the following aspects:

Each entity has it's own path:
/users, /boards, /lists, /cards.

When more information is needed for a request, for example POST boards/boardID/users, that information must come in the request body.

### Request Details

A request arrives in the TasksServer module, which currently only routes it to the TasksWebApi module.
The TasksWebApi module then routes the request into 1 of 4 possible routes: users, boards, lists, or cards. With each of those options having their own module, named {Entity}Routes.
The Module for an entity, for example UsersRoutes, then extracts the relevant information from the request and passes it on to the TasksServices module.
(_Inserir informação do TasksServices e do TasksDataMem_)
When the relevant information arrives back to the {Entity}Routes, then the Response is made and sent back to the TasksServer, which delivers it to it's consumer.

(_describe how a request goes through the different elements of your solution_)

(_describe the relevant classes/functions used internally in a request_)

(_describe how and where request parameters are validated_)

### Connection Management

(_describe how connections are created, used and disposed_, namely its relation with transaction scopes).

### Data Access

(_describe any created classes to help on data access_).

(_identify any non-trivial used SQL statements_).

### Error Handling/Processing

Any module under the TasksWebApi (or more specifically, it's {Entity}Routes) should throw an Exception when some type of problem is found, that results in the request not being fufilled.
That exception is then handled in the TasksWebApi, which captures the Exception and returns an Error Response to the server module, containing the correct information about the Exception that was thrown.
(_describe how errors are handled and their effects on the application behavior_).

## Critical Evaluation

(_enumerate the functionality that is not concluded and the identified defects_)

(_identify improvements to be made on the next phase_)

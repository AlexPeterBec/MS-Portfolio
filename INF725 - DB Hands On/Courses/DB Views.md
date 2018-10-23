# Transactions

* Logical unit of work consisting of one or more SQL statements.
* We use COMMIT and ROLLBACK to show or cancel an operation to the rest of the database.
* On most databases each SQL statement commits automatically.


# Views

* Databases are design to be used for different purposes, not specific for an application. Views help to store slightly different pictures of the same data.
* In some cases, we don't want all users to see the entire logical model.
* Any relation that is not part of the conceptual model but is visible to the user as a *virtual relation* is called a view.
* SQL expands the user query with the view query. This enables to mask a lot of complexity.

Extensibility Example : A UK company bought by another one. With a view it will be extended/modified virtually, to show units as metric units.

##### Structure

- Top Level : multiple stories for each view.
- Conceptual Schema
- Physical Schema
- Storage

##### View creation / destruction

Creation of a student view without the grades.
```
CREATE VIEW SecStudent AS
 SELECT studID, name, address, major
 FROM student

DROP VIEW
```



#### Data Partitioning

Making the queries efficient : Reduce the volume of data used.
* Horizontal : Projection on certain attributes.
* Vertical : Selection on certain values.

```
CREATE VIEW SeattleView AS
SELECT buyer, seller, product, store
FROM Person, Purchase
WHERE Person.city = "Seattle"
```

##### Update a View



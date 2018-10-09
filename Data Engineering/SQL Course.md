# SQL Course

### Define a Schema

```
Planes(ID: INT, Model: CHAR …)
```

### Constraints Keywords

- NOT NULL - Ensures that a column cannot have a NULL value
- UNIQUE - Ensures that all values in a column are different
- PRIMARY KEY - A combination of a NOT NULL and UNIQUE. Uniquely identifies each row in a table
- FOREIGN KEY - Uniquely identifies a row/record in another table
- CHECK - Ensures that all values in a column satisfies a specific condition
- DEFAULT - Sets a default value for a column when no value is specified
- INDEX - Use to create and retrieve data from the database very quickly

**Keys**  identify unique tuple values.
**Foreign Keys** : A student must exist in the student table to enroll in class table : Insert would be rejected otherwise.
**NULL values** Values does not exist, exists but is unknown...


### CREATE TABLE

```
CREATE TABLE table_name (
    column1 datatype constraint,
    column2 datatype constraint,
    column3 datatype constraint,
    ....
);
```

```
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);
```
Be careful with the order

# Commandes Hive

Launch Hive CLI
```
hive
>>>
```

Manage Databases
```
create database dbName;
```

# Advanced Hive

User Defined Function
```
CREATE FUNCTION strip AS ‘com.hadoop.hive.Strip’ USING JAR ‘/path/to/hive-examples.jar’;

hive > SELECT strip(‘bee’) FROM table;
```


Hive View
```
CREATE VIEW shorter_joint AS
SELECT * FROM people INNER JOIN cart
ON (cart.people_id=people.id) WHERE firstname = ‘john’;
```

Hive Partition
```
CREATE TABLE tracking (…)
PARTITIONED BY (jour STRING, site STRING)
```

Hive Buckets
```
CREATE TABLE clients (id_client INT, …)
CLUSTERED BY (id_client) INTO 10 BUCKETS;
```

# Commandes Hive

Launch Hive CLI
```
hive
>>>
```

Manage Databases
```
create database dbName;
use dbName;
show databases;
show tables;
```

#### Create Basic Hive table
```
CREATE TABLE dbName.tableName (
  field1 TYPE, field2 TYPE, ...)
PARTITIONED BY (field TYPE)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
LOCATION '/path/to/directory'
```

#### External Table
Ici le fichier CSV doit se trouver dans le dossier HDFS de la table (LOCATION)
```
use tp3;
create external table elus_mun (
  nomliste string, nompsn string, prepsn string, sexe string, naissance string, libcsp string, nat string
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.serde2.OpenCSVSerde'
WITH SERDEPROPERTIES (
   "separatorChar" = ",",
   "quoteChar" 	= "\""
)
LOCATION '/user/cloudera/TP3/elus_mun'
tblproperties("skip.header.line.count"="1")
```

#### Insert Into Table
Populate empty table from SELECT statement
https://cwiki.apache.org/confluence/display/Hive/LanguageManual%2BDML
```
set hive.exec.dynamic.partition.mode=nonstrict;
USE workDB;
INSERT OVERWRITE TABLE targetTable
PARTITION(partCol)
SELECT DISTINCT
  field1, field2 ... partCol
FROM existingTable
WHERE CLAUSE
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

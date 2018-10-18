## Streaming de données avec Kafka et NiFi

# Kafka

##### Telechargement de Kafka et demarrage des services Zookeeper, Kafka
```kafka_2.11_2.0.0/bin/zookeeper-server-start.sh config/zookeeper.properties```
```bin/kafka-server-start.sh config/server.properties```

##### Lancement d'un topic
```bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic fr.telecom.tpkafka.kafkatopictest```

##### Lister les topics
```bin/kafka-topics.sh --list --zookeeper localhost:2181```

##### Creation des consumers / producers
Lancement du producer
```bin/kafka-console-producer.sh --broker-list localhost:9092 --topic fr.telecom.tpkafka.kafkatopictest```

Lancement du consumer
```bin/kafka-console-consumer.sh --bootstrap-server localhost:9094 --topic fr.telecom.tpkafka.kafkatopictest --from-beginning```

On voit alors la consommation en temps réel des messages donnés au producer.

##### Cluster multi-broker
On edite deux nouveaux fichiers de config servers dans **config/server.properties** et on lance deux nouveaux brokers avec **kafka-server-start.sh**.

On lance alors un nouveau topic de 1 partition et replica 3 :
```bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic fr.telecom.tpkafka.topicreplication```


bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic fr.telecom.tpkafka.topicreplication

Topic: fr.telecom.tpkafka.topicreplication PartitionCount:1 ReplicationFactor:3	Configs:
Topic: fr.telecom.tpkafka.topicreplication Partition: 0	Leader: 2 Replicas: 2,0,1 Isr: 2,0,1

bin/kafka-console-producer.sh --broker-list localhost:9092,localhost:9093,localhost:9094 --topic fr.telecom.tpkafka.topicreplication

bin/kafka-console-consumer.sh


##### Creation d'un script bash qui ecrit dans un topic
Ecriture du temps en Nanosec

while true; do
	date=$(($(date +%s%N)/1000000))
	echo $date | /tmp/abec/kafka_2.11-2.0.0/bin/kafka-console-producer.sh --broker-list localhost:9092,localhost:9093 --topic $1
	sleep 1
done

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092,localhost:9093 --topic fr.telecom.tpkafka.topicreplication --from-beginning


# NiFi
Mise en route de Nifi et interfacage avec Kafka
```wget http://apache.crihan.fr/dist/nifi/1.7.1/nifi-1.7.1-bin.tar.gz
bin/nifi.sh start```








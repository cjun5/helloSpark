### spark streaming
```bash
# mac brew install
brew install netcat

# tcp listen 9000
nc -l -p 9000
```

#### kafka
```bash
./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topicA

./kafka-console-producer.sh --broker-list localhost:9092 --topic topicA
```
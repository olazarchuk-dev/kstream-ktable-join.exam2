# Kafka Streams Joins Explored: 'Stream-Stream Inner-Join', 'Stream-Stream Left-Join', 'Stream-Stream Outer-Join', 'Stream-Table Inner-Join', 'Stream-Table Left-Join'

The corresponding blog post for this repository can be found here: https://mydeveloperplanet.com/2019/10/30/kafka-streams-joins-explored


---

### Step 1

```shell
> sudo su -
> cat /opt/kafka/config/server.properties | grep log.dirs
```
Expected output:
```shell
log.dirs=/var/lib/kafka/data
```
```shell
> cd /var/lib/kafka/data
```
Delete **data** which saved the wrong cluster.id of last/failed session

### Step 2

In a separate terminal, Start ZooKeeper

```shell
> sudo su -
> /opt/kafka/bin/zookeeper-server-start.sh /opt/kafka/config/zookeeper.properties
```
Expected output:
```text
[2022-01-15 06:37:01,006] INFO binding to port 0.0.0.0/0.0.0.0:2181 (org.apache.zookeeper.server.NIOServerCnxnFactory)
```

### Step 3

In a separate terminal, Start Kafka broker

```shell
> sudo su -
> /opt/kafka/bin/kafka-server-start.sh /opt/kafka/config/server.properties
```
Expected output:
```text
[2022-01-15 06:38:04,234] INFO Connecting to zookeeper on localhost:2181 (kafka.server.KafkaServer)
[2022-01-15 06:38:04,245] INFO [ZooKeeperClient Kafka server] Initializing a new session to localhost:2181. (kafka.zookeeper.ZooKeeperClient)
...
[2022-01-15 07:54:33,508] INFO [BrokerToControllerChannelManager broker=0 name=forwarding]: Recorded new controller, from now on will use broker dell-5500:9092 (id: 0 rack: null) (kafka.server.BrokerToControllerRequestThread)
```

### Step 4

```shell
> /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic com.st.users.registration.event.user-registered --property "parse.key=true" --property "key.separator=:"
>:
>1:u1
>2:u2
>3:u3

> /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic com.st.users.registration.event.tags-changed --property "parse.key=true" --property "key.separator=:"
>:
>1:t1
>2:t2
>3:t3

> /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic com.st.users.registration.event.user-rollback --property "parse.key=true" --property "key.separator=:"
>:
>1:r1
>2:r2
>3:r3
```

Automatic create Joined-Topic(s) on Kafka:
* stream.table.inner.join-com.st.users.registration.event.tags-changed-STATE-STORE-000...-changelog
* stream.table.inner.join-com.st.users.registration.event.user-registered-STATE-STORE-000...-changelog
* stream.table.inner.join-com.st.users.registration.event.user-rollback-STATE-STORE-000...-changelog
* stream.table.inner.join-KSTREAM-JOINOTHER-000...-store-changelog
* stream.table.inner.join-KSTREAM-JOINTHIS-000...-store-changelog

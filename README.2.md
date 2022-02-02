# Kafka Streams Joins Explored: 'Stream-Stream Inner-Join', 'Stream-Stream Left-Join', 'Stream-Stream Outer-Join', 'Stream-Table Inner-Join', 'Stream-Table Left-Join'

* `Tutorial` https://www.instaclustr.com/blog/kongo-5-2-apache-kafka-streams-examples/
* `Repo` https://github.com/tomfaulhaber/geo-window

This repo has some WIP for some ideas I have about using Kafka Streams to process data based on simple spatio-temporal windows instead of just temporal windows.

Here we have Clojure and Java code which aggregates to simple hexbins.

More to come, I hope...

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

package kafkaModule3L16;

import org.apache.kafka.clients.producer.Producer;

interface Listener<K, V> {

    default void producerAdded(String id, Producer<K, V> producer) {
    }

    default void producerRemoved(String id, Producer<K, V> producer) {
    }

}
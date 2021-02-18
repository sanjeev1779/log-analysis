package com.sanjeev1779.log.analysis.log.consumer;

public class Main {

    public static void main(String[] args) {
        KafkaConsumerProcessor.runConsumer();
        KafkaConsumerProcessor.stopConsumer();
    }
}

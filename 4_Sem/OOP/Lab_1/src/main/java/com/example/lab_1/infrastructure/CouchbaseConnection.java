package com.example.lab_1.infrastructure;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;

import java.time.Duration;

public class CouchbaseConnection {
    private  static Cluster cluster;
    private  static Bucket bucket;

    public static void initialize() {
        cluster = Cluster.connect("127.0.0.1", "Administrator", "123456");

        bucket = cluster.bucket("Lab_1");

        bucket.waitUntilReady(Duration.ofSeconds(10));
    }

    public static Cluster getCluster() {
        return cluster;
    }
    public static Bucket getBucket() {
        return bucket;
    }

    public static void disconnect() {
        if(cluster != null) {
            cluster.disconnect();
        }
    }

}

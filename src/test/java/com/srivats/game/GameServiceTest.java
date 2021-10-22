package com.srivats.game;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameServiceTest {

    private GameServiceGrpc.GameServiceStub stub;

    @BeforeAll
    public void setup(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext().build();
        this.stub = GameServiceGrpc.newStub(managedChannel);

    }

    @Test
    public void clientGame() throws InterruptedException {


        CountDownLatch latch = new CountDownLatch(1);
        GameStateObserver gameStateObserver = new GameStateObserver(latch);
        StreamObserver<Die> dieStreamObserver = this.stub.roll(gameStateObserver);

        gameStateObserver.setDieStreamObserver(dieStreamObserver);
        gameStateObserver.roll();
        latch.await();
    }

}
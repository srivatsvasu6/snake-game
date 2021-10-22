package com.srivats.game;

import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class GameStateObserver  implements StreamObserver<GameState> {

    private StreamObserver<Die> dieStreamObserver;
    private final CountDownLatch latch;

    public GameStateObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(GameState gameState) {
        gameState.getPlayersList()
                .forEach(p -> System.out.println(p.getName() + " : "+ p.getPosition()));
        boolean gameOver = gameState.getPlayersList().stream().anyMatch(p -> p.getPosition() == 100);
        if(gameOver){
            System.out.println("Game Over!!");
            this.dieStreamObserver.onCompleted();
        }else{
            this.roll();
        }

        System.out.println("---------------------");
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        this.latch.countDown();
    }


    public void roll(){
        int die = ThreadLocalRandom.current().nextInt(1,7);
        Die die1 = Die.newBuilder().setValue(die).build();

        this.dieStreamObserver.onNext(die1);

    }

    public void setDieStreamObserver(StreamObserver<Die> dieStreamObserver) {
        this.dieStreamObserver = dieStreamObserver;
    }
}

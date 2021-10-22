package com.srivats.game;

import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class DieStreamingRequest implements StreamObserver<Die> {
    private final StreamObserver<GameState> responseObserver;
    private Player client;
    private Player server;

    public DieStreamingRequest(StreamObserver<GameState> responseObserver, Player client, Player server) {
        this.responseObserver = responseObserver;
        this.client = client;
        this.server = server;
    }

    @Override
    public void onNext(Die die) {
        this.client = this.getNewPlayerPosition(this.client, die.getValue());
        if(this.client.getPosition() != 100){
            this.server = this.getNewPlayerPosition(this.server, ThreadLocalRandom.current().nextInt(1, 7));
        }else{

            System.out.println();
            System.out.println("Game over!!");
            this.responseObserver.onCompleted();
            return;

        }
        this.responseObserver.onNext(this.getGameState());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        this.responseObserver.onCompleted();
    }

    private GameState getGameState(){
        return  GameState.newBuilder()
                .addPlayers(this.server)
                .addPlayers(this.client)
                .build();

    }

    private Player getNewPlayerPosition(Player player, int dieValue) {

        int pos = player.getPosition() + dieValue;
            pos = SnakeAndLadderMap.getPosition(pos);
        if (pos <= 100) {
            player = player.toBuilder()
                    .setPosition(pos).build();
        }

        return player;

    }
}

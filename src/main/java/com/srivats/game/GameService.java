package com.srivats.game;

import io.grpc.stub.StreamObserver;

public class GameService extends GameServiceGrpc.GameServiceImplBase {
    @Override
    public StreamObserver<Die> roll(StreamObserver<GameState> responseObserver) {
        Player client = Player.newBuilder().setPosition(0).setName("client")
                .build();
        Player server = Player.newBuilder().setPosition(0).setName("server")
                .build();

        return new DieStreamingRequest(responseObserver, client, server);
    }
}

package com.srivats.game;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcMain {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(6565)
                .addService(new GameService())
                .build();

        server.start();
        System.out.println("Server started!!");
        server.awaitTermination();
    }
}

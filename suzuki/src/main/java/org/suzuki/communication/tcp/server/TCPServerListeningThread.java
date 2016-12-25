package org.suzuki.communication.tcp.server;

import org.suzuki.communication.tcp.server.exception.MessageReadException;
import org.suzuki.communication.tcp.server.exception.ServerStartException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class TCPServerListeningThread extends Thread {

    private LinkedBlockingQueue<String> messageQueue;

    private int port;

    TCPServerListeningThread(int port, LinkedBlockingQueue<String> messageQueue) {
        this.port = port;
        this.messageQueue = messageQueue;
    }

    private ServerSocket startServer(int port) throws ServerStartException {
        try {
            return new ServerSocket(port);
        } catch(IOException e) {
            throw new ServerStartException(e);
        }
    }

    private void readMessage(InputStream inputStream) throws MessageReadException {
        try {
            // use buffered reader
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(inputStream));

            // read string
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = inFromClient.readLine()) != null) {
                sb.append(line);//.append("\n");
            }
            String message = sb.toString();

            // add to queue
            messageQueue.offer(message);
        } catch (IOException e) {
            throw new MessageReadException(e);
        }
    }

    @Override
    public void run() {
        ServerSocket welcomeSocket = null;
        Socket connectionSocket = null;
        try {
            // start server
            welcomeSocket = startServer(port);

            while(true) {
                // accept connection
                connectionSocket = welcomeSocket.accept();

                // read message
                readMessage(connectionSocket.getInputStream()); //TODO this need to be in threads
            }
        } catch (MessageReadException e){
            throw new RuntimeException(e);  //TODO some custom handling
        } catch (ServerStartException e) {
            throw new RuntimeException(e);  //TODO some custom handling
        } catch (IOException e) {
            throw new RuntimeException(e);  //TODO some custom handling
        } finally {
            try{
                if (welcomeSocket != null) {
                    welcomeSocket.close();
                }

                if(connectionSocket != null) {
                    connectionSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
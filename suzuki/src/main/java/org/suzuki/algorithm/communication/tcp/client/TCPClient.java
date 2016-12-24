package org.suzuki.algorithm.communication.tcp.client;

import org.suzuki.algorithm.communication.tcp.client.Exception.SendException;

import java.io.*;
import java.net.Socket;

public class TCPClient {

    private String host;

    private int port;

    private Socket clientSocket;

    public TCPClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void send(String json) {
        try {
            clientSocket = new Socket(host, port);

//            String sentence;
////            String modifiedSentence;
//            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
//            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
////            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            sentence = inFromUser.readLine();
//            outToServer.writeBytes(sentence + '\n');
////            modifiedSentence = inFromServer.readLine();
////            System.out.println("FROM SERVER: " + modifiedSentence);
//            clientSocket.close();

            OutputStream outStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outStream);
            out.print(json);
            out.flush();//TODO do we need these?
            clientSocket.close();
        } catch (IOException e){
            throw new SendException(e);
        }
    }

}
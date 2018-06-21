package org.academiadecodigo.org;

import org.academiadecodigo.org.httpResponse.HTTPRequest;
import org.academiadecodigo.org.httpResponse.HTTPResponse;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by codecadet on 19/06/2018.
 */
public class WebServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private HTTPRequest request;
    private HTTPResponse response;
    private int portNumber;

    public WebServer(int portNumber) {
        this.portNumber = portNumber;
    }

    public void init() {
        try {
            System.out.println("Creating a socket");
            serverSocket = new ServerSocket(portNumber);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("CONNECTED TO SERVER ");

                OutputStream outputStream = clientSocket.getOutputStream();
                System.out.println("CREATED STREAM");

                request = new HTTPRequest(clientSocket.getInputStream());
                System.out.println("OPENED INPUT STREAM");
                request.getRequest();
                System.out.println(request);
                System.out.println("READ HEADER");

                response = request.getResponseType();
                response.prepareResponse();
                System.out.println("PREPARED RESPONSE");
                System.out.println(response);

                response.send(outputStream);
                System.out.println("SENT RESPONSE");

                clientSocket.close();

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
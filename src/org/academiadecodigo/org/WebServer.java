package org.academiadecodigo.org;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by codecadet on 19/06/2018.
 */
public class WebServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private DataOutputStream dataOutputStream;

    public void init() {

        try {
            System.out.println("Creating a socket");
            serverSocket = new ServerSocket(8080);

            clientSocket = serverSocket.accept();

            System.out.println("Creating a buffered reader and an output stream");
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            start();

            closeConnections();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {
        try {
            System.out.println("Reading a line");
            String header = bufferedReader.readLine();

            if (header == null) {
                return;
            }

            System.out.println("Reading header");
            readHeader(header);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readHeader(String header) {
        String[] headerParts = header.split(" ");

        String verb = headerParts[0];
        String path = headerParts[1];

        if (verb.equals("GET")) {
            get(path);
        }
    }

    public void get(String filePath) {
        HTTPcode httpCode;
        String contentTypeHeader;

        String extension = getExtension(filePath);

        if (filePath.endsWith("/")) {
            filePath = filePath + "index.html";
        }

        File file = new File("www" + filePath);

        if (file.exists()) {
            httpCode = HTTPcode.c200;
            contentTypeHeader = getContentTypeHeader(extension);
        } else {
            httpCode = HTTPcode.c404;
            file = new File("www/404.html");
            contentTypeHeader = "Content-Type: text/html; charset=UTF-8\r\n";
        }

        try {
            byte[] fileAsByteArray = Files.readAllBytes(file.toPath());

            dataOutputStream.writeBytes(httpCode.getHeader() +
                    contentTypeHeader +
                    "Content-Length: " + fileAsByteArray.length + "\r\n" +
                    "\r\n");
            dataOutputStream.write(Files.readAllBytes(file.toPath()));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getContentTypeHeader(String extension) {
        for (FileType fileType : FileType.values()) {
            if (extension.equals(fileType.getExtension())) {
                return fileType.getContentType();
            }
        }
        return "";
    }

    private void closeConnections() {
        try {
            System.out.println("closing");
            dataOutputStream.close();
            bufferedReader.close();
            clientSocket.close();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("wait");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getExtension(String path) {
        String[] pathDecomposed = path.split("\\.");

        if (pathDecomposed.length > 0) {
            int lastPartOfPath = pathDecomposed.length - 1;
            String extension = pathDecomposed[lastPartOfPath];
            System.out.println(extension);
            return extension;
        }

        return "";
    }
}

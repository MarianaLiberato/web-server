package org.academiadecodigo.org.httpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPRequest {
    private BufferedReader bufferedReader;
    private String header;
    private String path;

    public HTTPRequest(InputStream inputStream){
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void getRequest() {
        header = "";
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null && !line.equals("")){
                header = header + line;
            }
            System.out.println(header);

            closeReader();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public HTTPResponse getResponseType() {
        String[] headerParts = header.split(" ");

        path = headerParts[1];

        if (headerParts[0].equals("GET")) {
            return new GetResponse(path);
        }

        return null;
    }

    private void closeReader(){
        try {
            bufferedReader.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
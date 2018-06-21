package org.academiadecodigo.org.httpResponse;
import org.academiadecodigo.org.FileType;
import org.academiadecodigo.org.HTTPcode;
import java.io.*;
import java.nio.file.Files;

public class GetResponse implements HTTPResponse {

    private String path;
    private File file;
    private HTTPcode httpCode;
    private String header;
    private FileInputStream fileInputStream;
    private DataOutputStream dataOutputStream;

    public GetResponse(String path) {
        this.path = "www" + path;
    }

    public void prepareResponse() {
        checkFile();
        setHeader();
    }

    private void checkFile() {
        if (path.endsWith("/")) {
            path = path + "index.html";
        }

        file = new File(path);

        if (file.exists()) {
            httpCode = HTTPcode.c200;
        } else {
            httpCode = HTTPcode.c404;
            file = new File("www/404.html");
        }
    }

    private void setHeader() {
        String contentTypeHeader;
        String extension = getExtension(path);

        contentTypeHeader = getContentTypeHeader(extension);

        if (httpCode == HTTPcode.c404) {
            contentTypeHeader = "Content-Type: text/html; charset=UTF-8\r\n";
        }

        header = httpCode.getHeader() + contentTypeHeader + contentLength() + "\r\n";
    }

    public void send(OutputStream outputStream){
        dataOutputStream = new DataOutputStream(outputStream);

        int bytesRead = 0;
        byte[] buffer = new byte[1024];

        try {
            /*dataOutputStream.writeBytes("HTTP/1.0 200 Document Follows\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "Content-Length: 12 \r\n" +
                    "\r\n");*/

            fileInputStream = new FileInputStream(path);

            dataOutputStream.writeBytes(header);
            System.out.println(header);

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
                dataOutputStream.flush();
            }
            System.out.println("finished while");

        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnections();
        }
    }

    private String contentLength() {
        try {
            return "Content-Length: " + Files.readAllBytes(file.toPath()).length + "\r\n";
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return "";
    }

    private String getContentTypeHeader(String extension) {
        for (FileType fileType : FileType.values()) {
            if (extension.equals(fileType.getExtension())) {
                return fileType.getContentType();
            }
        }
        return "";
    }

    private String getExtension(String path) {
        String[] pathDecomposed = path.split("\\.");

        if (pathDecomposed.length > 0) {
            int lastPartOfPath = pathDecomposed.length - 1;
            String extension = pathDecomposed[lastPartOfPath];
            System.out.println(extension);
            return extension;
        }

        return "Content-Type: text/html; charset=UTF-8\r\n";
    }

    private void closeConnections(){

      try {
            fileInputStream.close();
            dataOutputStream.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}

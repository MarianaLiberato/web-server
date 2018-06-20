package org.academiadecodigo.org;

/**
 * Created by codecadet on 19/06/2018.
 */
public enum FileType {
    HTML("html", "Content-Type: text/html; charset=UTF-8\r\n"),
    JPEG("jpeg", "Content-Type: image/jpeg\r\n)"),
    PNG("png", "Content-Type: image/png\r\n"),
    ICO("ico", "Content-Type: image/x-icon\r\n");

    private String contentType;
    private String extension;

    FileType(String extension, String contentType){
        this.extension = extension;
        this.contentType = contentType;
    }

    public String getContentType(){
        return contentType;
    }

    public String getExtension(){
        return extension;
    }
}

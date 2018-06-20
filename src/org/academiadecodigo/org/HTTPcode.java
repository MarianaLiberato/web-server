package org.academiadecodigo.org;

/**
 * Created by codecadet on 19/06/2018.
 */
public enum HTTPcode {
    c200("HTTP/1.0 200 Document Follows\r\n"),
    c404("HTTP/1.0 404 Not Found\r\n");

    private String header;

    HTTPcode(String header){
        this.header = header;
    }

    public String getHeader(){
        return header;
    }

}

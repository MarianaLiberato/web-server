package org.academiadecodigo.org;

/**
 * Created by codecadet on 19/06/2018.
 */
public class Main {
    public static void main(String[] args) {
        WebServer webServer = new WebServer(60000);
            webServer.init();
            webServer.start();
    }
}

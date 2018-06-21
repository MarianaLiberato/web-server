package org.academiadecodigo.org.httpResponse;

import java.io.OutputStream;

public interface HTTPResponse {

    public void prepareResponse();
    public void send(OutputStream outputStream);

}

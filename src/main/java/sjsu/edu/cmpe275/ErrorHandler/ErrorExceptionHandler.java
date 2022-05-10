package sjsu.edu.cmpe275.ErrorHandler;

import org.springframework.web.client.HttpClientErrorException;

public class ErrorExceptionHandler {
//    private HttpClientErrorException.BadRequest badRequest;
//    public ErrorExceptionHandler(HttpClientErrorException.BadRequest badRequest) {
//        this.badRequest = badRequest;
//    }
//
//    public ErrorExceptionHandler(BadRequest errorObject) {
//
//    }
//
//    public HttpClientErrorException.BadRequest getBadRequest() {
//        return badRequest;
//    }
//
//    public void setBadRequest(HttpClientErrorException.BadRequest badRequest) {
//        this.badRequest = badRequest;
//    }

    private BadRequest badRequest;

    public ErrorExceptionHandler(){};
    public ErrorExceptionHandler(BadRequest badRequest) {
        this.badRequest = badRequest;
    }

    public BadRequest getBadRequest() {
        return badRequest;
    }

    public void setBadRequest(BadRequest badRequest) {
        this.badRequest = badRequest;
    }

}

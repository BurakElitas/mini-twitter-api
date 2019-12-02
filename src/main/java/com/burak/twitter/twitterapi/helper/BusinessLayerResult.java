package com.burak.twitter.twitterapi.helper;

import java.util.ArrayList;
import java.util.List;

public class BusinessLayerResult<T> {

    private T result;
    private List<String> errorMessages;
    public BusinessLayerResult(){
        errorMessages=new ArrayList<>();
        }


    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}


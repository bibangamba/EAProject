package com.cs544.project.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomNotFoundException extends Exception{
    public CustomNotFoundException(String message){
        super(message);
    }
}

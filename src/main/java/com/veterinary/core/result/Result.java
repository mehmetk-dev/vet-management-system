package com.veterinary.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Result {

    private boolean status;
    private String message;
    private String code;

}

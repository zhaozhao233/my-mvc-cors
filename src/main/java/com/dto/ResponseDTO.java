package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private String code;
    private String msg;
    private T data;

//    public ResponseDTO() {
//
//    }

//    public ResponseDTO(String code, String msg, T data) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }
}

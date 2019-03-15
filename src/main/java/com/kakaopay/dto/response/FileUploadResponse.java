package com.kakaopay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    private String name;
    private long size;
    private int rows;
    private String msg;
}

package com.yaktest.gerichte.gerichte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GerichtRequest {
    private String name;
    private GerichtZutat[] zutaten;
    private String anleitung;
    private List<String> tags;
    private List<byte[]> images;
    private boolean isOpen;
}

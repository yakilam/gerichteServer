package com.yaktest.gerichte.gerichte;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GerichtResponse {
    private String name;
    private String author;
    private List<GerichtZutat> zutaten;
    private String anleitung;
    private List<String> tags;
    private List<String> images;
}

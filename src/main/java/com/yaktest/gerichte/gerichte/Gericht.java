package com.yaktest.gerichte.gerichte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "gerichte")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Gericht {

    @Id
    private String id;
    private String name;
    private String author;
    private List<GerichtZutat> zutaten;
    private String anleitung;
    private List<String> tags;
    private boolean isOpen;
    private List<String> images;


}

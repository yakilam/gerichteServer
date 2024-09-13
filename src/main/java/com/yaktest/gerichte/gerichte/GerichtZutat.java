package com.yaktest.gerichte.gerichte;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GerichtZutat {
    private String name;
    private int menge;
    private String einheit;
}

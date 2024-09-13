package com.yaktest.gerichte.gerichte;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PlanRequest {
    private int days;
    private List<GerichtZutat> zutaten;
}

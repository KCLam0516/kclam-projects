package com.example.finalyearproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class carListItems {
    private int imageResources;
    private String carName;
    private String ownerName;
    private String price;
    private String key_id;
}
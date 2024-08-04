package logX.TTT.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LocationDTO {
    private String name;
    private double latitude;
    private double longitude;
}

package logX.TTT.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationRangeDTO {
    private double southWestLat;
    private double southWestLng;
    private double northEastLat;
    private double northEastLng;
}
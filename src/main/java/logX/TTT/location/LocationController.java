package logX.TTT.location;

import logX.TTT.location.model.LocationRangeDTO;
import logX.TTT.location.model.MarkerWithPostsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/posts")
    public ResponseEntity<List<MarkerWithPostsDTO>> getMarkerWithPostsByLocationRange(@RequestBody LocationRangeDTO locationRangeDTO) {
        double northEastLat = locationRangeDTO.getNorthEastLat();
        double northEastLng = locationRangeDTO.getNorthEastLng();
        double southWestLat = locationRangeDTO.getSouthWestLat();
        double southWestLng = locationRangeDTO.getSouthWestLng();

        List<MarkerWithPostsDTO> markers = locationService.getMarkersWithPostsByLocationRange(southWestLat, southWestLng, northEastLat, northEastLng);
        return ResponseEntity.ok(markers);
    }
}

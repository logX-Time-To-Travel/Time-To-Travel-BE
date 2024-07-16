package logX.TTT.location;

import logX.TTT.location.model.MarkerWithPostsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @GetMapping("/posts")
    public ResponseEntity<List<MarkerWithPostsDTO>> getMarkerWithPostsByLocationRange (
            @RequestParam double northEastLat,
            @RequestParam double northEastLon,
            @RequestParam double southWestLat,
            @RequestParam double southWestLon
    ) {
        List<MarkerWithPostsDTO> markers = locationService.getMarkersWithPostsByLocationRange(northEastLat, northEastLon, southWestLat, southWestLon);
        return ResponseEntity.ok(markers);
    }
}

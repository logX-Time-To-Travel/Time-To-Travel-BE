package logX.TTT.location;

import logX.TTT.location.model.MarkerWithPostsDTO;
import logX.TTT.post.model.PostSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<MarkerWithPostsDTO> getMarkersWithPostsByLocationRange(double southWestLat, double southWestLng, double northEastLat, double northEastLng) {
        List<Location> locations = locationRepository.findByLocationRange(southWestLat, southWestLng, northEastLat, northEastLng);

        return locations.stream()
                .collect(Collectors.groupingBy(
                        location -> new MarkerWithPostsDTO(location.getName(), location.getLatitude(), location.getLongitude(), null),
                        Collectors.mapping(Location::getPost, Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> {
                    MarkerWithPostsDTO markerDTO = entry.getKey();
                    List<PostSummaryDTO> postSummaries = entry.getValue().stream()
                            .map(post -> new PostSummaryDTO(
                                    post.getId(),
                                    post.getTitle(),
                                    20,// Entity 미완성 - 추후 교체: post.getLikeCount(),
                                    40,// Entity 미완성 - 추후 교체: post.getViewCount(),
                                    "image/image.jpg"// Entity 미완성 - 추후 교체: post.getImageUrl()
                            ))
                            .collect(Collectors.toList());
                    markerDTO.setPosts(postSummaries);
                    return markerDTO;
                })
                .collect(Collectors.toList());
    }
}
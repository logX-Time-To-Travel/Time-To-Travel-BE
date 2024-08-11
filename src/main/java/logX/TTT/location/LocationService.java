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
                                    post.getLikes().size(), // 좋아요 수
                                    post.getViews().size(), // 조회수
                                    post.getImageUrl(),
                                    post.getCreatedAt()
                            ))
                            .collect(Collectors.toList());
                    markerDTO.setPosts(postSummaries);
                    return markerDTO;
                })
                .collect(Collectors.toList());
    }
}
package alexander.sergeev.mapper;

import alexander.sergeev.dto.request.RequestDto;
import alexander.sergeev.model.Request;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestMapper {

    public RequestDto mapRequestToDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getCreated(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus());
    }

}

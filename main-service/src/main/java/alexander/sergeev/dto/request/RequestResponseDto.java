package alexander.sergeev.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestResponseDto {

    private Set<RequestDto> confirmedRequests;

    private Set<RequestDto> rejectedRequests;

}

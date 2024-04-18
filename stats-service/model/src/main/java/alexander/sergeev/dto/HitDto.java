package alexander.sergeev.dto;

import alexander.sergeev.validation.ValidationMarker;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class HitDto {

    private Long id;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Incoming hit app field is blank!")
    @Size(groups = ValidationMarker.OnCreate.class, max = 128,
            message = "Incoming hit app field is bigger than 128 characters!")
    private String app;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Incoming hit uri field is blank!")
    @Size(groups = ValidationMarker.OnCreate.class, max = 128,
            message = "Incoming hit uri field is bigger than 128 characters!")
    private String uri;

    @NotBlank(groups = ValidationMarker.OnCreate.class,
            message = "Incoming hit ip field is blank!")
    @Size(groups = ValidationMarker.OnCreate.class, max = 32,
            message = "Incoming hit ip field is bigger than 32 characters!")
    private String ip;

    @NotNull(groups = ValidationMarker.OnCreate.class,
            message = "Incoming hit timestamp field is null!")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String timestamp;

}

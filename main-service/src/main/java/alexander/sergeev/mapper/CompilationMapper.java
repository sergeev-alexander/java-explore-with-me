package alexander.sergeev.mapper;

import alexander.sergeev.dto.compilation_dto.CompilationDto;
import alexander.sergeev.dto.compilation_dto.NewCompilationDto;
import alexander.sergeev.model.Compilation;
import lombok.experimental.UtilityClass;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public Compilation mapNewDtoToCompilation(NewCompilationDto newCompilationDto) {
        return new Compilation(
                null,
                newCompilationDto.getTitle(),
                newCompilationDto.getPinned() != null && newCompilationDto.getPinned(),
                null);
    }

    public CompilationDto mapCompilationToDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents() == null ? Set.of() : compilation.getEvents()
                        .stream()
                        .map(EventMapper::mapEventToShortDto)
                        .collect(Collectors.toSet()));
    }

}

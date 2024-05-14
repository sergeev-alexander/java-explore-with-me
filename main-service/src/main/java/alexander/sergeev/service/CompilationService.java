package alexander.sergeev.service;

import alexander.sergeev.dto.compilation_dto.CompilationDto;
import alexander.sergeev.dto.compilation_dto.NewCompilationDto;
import alexander.sergeev.dto.compilation_dto.UpdateCompilationDto;
import alexander.sergeev.mapper.CompilationMapper;
import alexander.sergeev.model.Compilation;
import alexander.sergeev.repository.CompilationRepository;
import alexander.sergeev.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public List<CompilationDto> getCompilationsByPublic(Boolean pinned, Pageable pageable) {
        if (pinned == null)
            return compilationRepository.findAll(pageable)
                    .stream()
                    .map(CompilationMapper::mapCompilationToDto)
                    .collect(Collectors.toList());
        else return compilationRepository.findByPinned(pinned, pageable)
                .stream()
                .map(CompilationMapper::mapCompilationToDto)
                .collect(Collectors.toList());
    }

    public CompilationDto getCompilationByIdByPublic(Long id) {
        return CompilationMapper.mapCompilationToDto(compilationRepository.getCompilationById(id));
    }

    public CompilationDto postCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = CompilationMapper.mapNewDtoToCompilation(newCompilationDto);
        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty())
            compilation.setEvents(eventRepository.findByIdIn(newCompilationDto.getEvents()));
        return CompilationMapper.mapCompilationToDto(compilationRepository.save(compilation));
    }

    public CompilationDto patchCompilationById(Long compId, UpdateCompilationDto updateCompilationDto) {
        Compilation compilation = compilationRepository.getCompilationById(compId);
        if (updateCompilationDto.getPinned() != null)
            compilation.setPinned(updateCompilationDto.getPinned());
        if (updateCompilationDto.getTitle() != null)
            compilation.setTitle(updateCompilationDto.getTitle());
        if (updateCompilationDto.getEvents() != null && !updateCompilationDto.getEvents().isEmpty())
            compilation.setEvents(eventRepository.findByIdIn(updateCompilationDto.getEvents()));
        return CompilationMapper.mapCompilationToDto(compilationRepository.save(compilation));
    }

    public void deleteCompilationById(Long compId) {
        compilationRepository.delete(compilationRepository.getCompilationById(compId));
    }

}

package ps.demo.commupload.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ps.demo.commupload.dto.UploadMetaDto;
import ps.demo.commupload.entity.UploadMeta;
import ps.demo.commupload.mapping.UploadMetaMapper;
import ps.demo.commupload.repository.UploadMetaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UploadMetaService {

    @Autowired
    private UploadMetaRepository uploadMetaRepository;

    public List<UploadMetaDto> findAll() {
        List<UploadMeta> uploadMetaList = uploadMetaRepository.findAll();
        return uploadMetaList.stream().map(e -> UploadMetaMapper.INSTANCE.toDto(e)).collect(Collectors.toList());
    }

    public Optional<UploadMetaDto> findById(Long id) {
        Optional<UploadMeta> uploadMetaDto = uploadMetaRepository.findById(id);
        return uploadMetaDto.stream().map(e -> UploadMetaMapper.INSTANCE.toDto(e)).findAny();

    }

    @Transactional
    public UploadMetaDto save(UploadMetaDto uploadMetaDto) {
        UploadMeta uploadMeta1 = uploadMetaRepository.save(UploadMetaMapper.INSTANCE.toEntity(uploadMetaDto));
        return UploadMetaMapper.INSTANCE.toDto(uploadMeta1);
    }

    @Transactional
    public void deleteById(Long id) {
        uploadMetaRepository.deleteById(id);
    }

    public List<UploadMetaDto> findByUploadedDateAfter(LocalDate date) {
        List<UploadMeta> uploadMetaList = uploadMetaRepository.findByUploadedDateAfter(date);
        return uploadMetaList.stream().map(e -> UploadMetaMapper.INSTANCE.toDto(e)).collect(Collectors.toList());
    }
}
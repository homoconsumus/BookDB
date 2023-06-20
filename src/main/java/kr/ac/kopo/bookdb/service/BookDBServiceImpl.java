package kr.ac.kopo.bookdb.service;

import io.micrometer.observation.annotation.Observed;
import kr.ac.kopo.bookdb.dto.BookDBDTO;
import kr.ac.kopo.bookdb.dto.PageResultDTO;
import kr.ac.kopo.bookdb.dto.PageRequestDTO;
import kr.ac.kopo.bookdb.entity.BookDB;
import kr.ac.kopo.bookdb.repository.BookDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class BookDBServiceImpl implements BookDBService{

    private final BookDBRepository repository; // repository는 final로 선언

    @Override
    public Long register(BookDBDTO dto){
        log.info("DTO-----------------------------");
        log.info(dto);

        BookDB entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getBno();
    }

    @Override
    public BookDBDTO read(Long bno){
        Optional<BookDB> result = repository.findById(bno);
        return result.isPresent()?entityToDto(result.get()):null;
    }

    @Override
    public PageResultDTO<BookDBDTO, BookDB> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        Page<BookDB> result = repository.findAll(pageable);

        Function<BookDB, BookDBDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}

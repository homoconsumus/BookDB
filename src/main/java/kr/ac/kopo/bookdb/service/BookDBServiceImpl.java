package kr.ac.kopo.bookdb.service;

import kr.ac.kopo.bookdb.dto.BookDBDTO;
import kr.ac.kopo.bookdb.entity.BookDB;
import kr.ac.kopo.bookdb.repository.BookDBRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
}

package kr.ac.kopo.bookdb.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.bookdb.dto.BookDBDTO;
import kr.ac.kopo.bookdb.dto.PageResultDTO;
import kr.ac.kopo.bookdb.dto.PageRequestDTO;
import kr.ac.kopo.bookdb.entity.BookDB;
import kr.ac.kopo.bookdb.entity.QBookDB;
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
    public void remove(Long bno){
        repository.deleteById(bno);
    }

    @Override
    public void modify(BookDBDTO dto){
        Optional<BookDB> result = repository.findById(dto.getBno());
        if(result.isPresent()){
            BookDB entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeWriter(dto.getWriter());
            entity.changePublisher(dto.getPublisher());
            entity.changeReleasedate(dto.getReleasedate());
            entity.changePrice(dto.getPrice());
            repository.save(entity);
        }
    }

    @Override
    public PageResultDTO<BookDBDTO, BookDB> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 검색조건 처리

        Page<BookDB> result = repository.findAll(booleanBuilder, pageable); // querydsl사용

        Function<BookDB, BookDBDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        String keyword_title = requestDTO.getKeyword_title();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QBookDB qBookDB = QBookDB.bookDB;

        BooleanExpression expression = qBookDB.bno.gt(0L); // bno > 0일 경우만 생성
        booleanBuilder.and(expression);
        if (type==null||type.trim().length()==0){
            // 검색 조건이 없는 경우
            return booleanBuilder;
        }

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")){
            conditionBuilder.or(qBookDB.title.contains(keyword_title));
        }
        if(type.contains("w")){
            conditionBuilder.or(qBookDB.writer.contains(keyword_title));
        }
        if(type.contains("p")){
            conditionBuilder.or(qBookDB.publisher.contains(keyword_title));
        }

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}

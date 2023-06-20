package kr.ac.kopo.bookdb.service;

import kr.ac.kopo.bookdb.dto.BookDBDTO;
import kr.ac.kopo.bookdb.entity.BookDB;

import java.awt.print.Book;

public interface BookDBService {
    Long register(BookDBDTO dto);

    default BookDB dtoToEntity(BookDBDTO dto){
        BookDB entity = BookDB.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .publisher(dto.getPublisher())
                .releasedata(dto.getReleasedata())
                .price(dto.getPrice())
                .build();
        return entity;
    }

    default BookDBDTO entityToDto(BookDB entity){
        BookDBDTO dto = BookDBDTO.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .writer(entity.getWriter())
                .publisher(entity.getPublisher())
                .releasedata(entity.getReleasedata())
                .price(entity.getPrice())
                .build();
        return dto;
    }
}

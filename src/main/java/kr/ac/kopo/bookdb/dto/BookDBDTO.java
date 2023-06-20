package kr.ac.kopo.bookdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDBDTO {
    private Long bno;
    private String title;
    private String writer;
    private String publisher;
    private LocalDate releasedate;
    private Long price;
}

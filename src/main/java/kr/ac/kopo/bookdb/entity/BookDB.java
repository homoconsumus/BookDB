package kr.ac.kopo.bookdb.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDB extends BaseEntity{
    // 책 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    // 책 제목
    @Column(length = 100, nullable = false)
    private String title;

    // 저자
    @Column(length = 100, nullable = false)
    private String writer;

    // 출판사
    @Column(length = 100, nullable = false)
    private String publisher;

    // 발매일
    @Column(name = "releasedate")
    private LocalDate releasedata;

    // 가격
    @Column(name = "price")
    private Long price;

}

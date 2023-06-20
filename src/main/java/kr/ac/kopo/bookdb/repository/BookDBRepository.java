package kr.ac.kopo.bookdb.repository;

import kr.ac.kopo.bookdb.entity.BookDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BookDBRepository extends JpaRepository<BookDB, Long>, QuerydslPredicateExecutor<BookDB> {
}

package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.Kitap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KitapRepository extends JpaRepository<Kitap, Long> {

    //Dram ve Hikaye türündeki kitapları listeleyin. JOIN kullanmadan yapın.
    String QUESTION_1 = "SELECT * FROM kitap WHERE turno IN (SELECT turno FROM tur WHERE ad = 'Dram' OR ad = 'Hikaye')";
    @Query(value = QUESTION_1, nativeQuery = true)
    List<Kitap> findBooks();


    String QUESTION_10 = "SELECT o.ad, o.soyad, COUNT(i.kitapno) AS kitap_sayisi FROM ogrenci o LEFT JOIN islem i ON o.ogrno = i.ogrno GROUP BY o.ad, o.soyad ORDER BY kitap_sayisi DESC";
    @Query(value = QUESTION_10, nativeQuery = true)
    Double findAvgPointOfBooks();


}

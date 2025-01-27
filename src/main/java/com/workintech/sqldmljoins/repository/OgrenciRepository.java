package com.workintech.sqldmljoins.repository;

import com.workintech.sqldmljoins.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OgrenciRepository extends JpaRepository<Ogrenci, Long> {


    //Kitap alan öğrencilerin öğrenci bilgilerini listeleyin..
    String QUESTION_2 = "SELECT * FROM ogrenci WHERE ogrno IN \n" +
            "(SELECT ogrno FROM islem);\n";
    @Query(value = QUESTION_2, nativeQuery = true)
    List<Ogrenci> findStudentsWithBook();


    //Kitap almayan öğrencileri listeleyin.
    String QUESTION_3 = "SELECT \n" +
            "    o.ogrno, o.ad, o.soyad \n" +
            "FROM \n" +
            "    ogrenci o\n" +
            "LEFT JOIN \n" +
            "    islem i ON o.ogrno = i.ogrno\n" +
            "WHERE \n" +
            "    i.ogrno IS NULL;";
    @Query(value = QUESTION_3, nativeQuery = true)
    List<Ogrenci> findStudentsWithNoBook();

    //10A veya 10B sınıfındaki öğrencileri sınıf ve okuduğu kitap sayısını getirin.
    String QUESTION_4 = "SELECT sinif, COUNT(islem.kitapno) AS kitap_sayisi\n" +
            "FROM ogrenci\n" +
            "JOIN islem ON ogrenci.ogrno = islem.ogrno\n" +
            "WHERE sinif IN ('10A', '10B')\n" +
            "GROUP BY sinif;\n";
    @Query(value = QUESTION_4, nativeQuery = true)
    List<KitapCount> findClassesWithBookCount();

    //Öğrenci tablosundaki öğrenci sayısını gösterin
    String QUESTION_5 = "SELECT COUNT(ogrno) FROM ogrenci";
    @Query(value = QUESTION_5, nativeQuery = true)
    Integer findStudentCount();

    //Öğrenci tablosunda kaç farklı isimde öğrenci olduğunu listeleyiniz.
    String QUESTION_6 = "SELECT COUNT(DISTINCT ad) AS farkli_isim_sayisi FROM ogrenci";
    @Query(value = QUESTION_6, nativeQuery = true)
    Integer findUniqueStudentNameCount();

    //--İsme göre öğrenci sayılarının adedini bulunuz.
    //--Ali: 2, Mehmet: 3
    String QUESTION_7 = "SELECT ad, COUNT(*) AS ogrenci_sayisi FROM ogrenci GROUP BY ad ORDER BY ogrenci_sayisi DESC";
    @Query(value = QUESTION_7, nativeQuery = true)
    List<StudentNameCount> findStudentNameCount();


    String QUESTION_8 = "SELECT sinif, COUNT(*) AS ogrenci_sayisi FROM ogrenci GROUP BY sinif ORDER BY sinif";
    @Query(value = QUESTION_8, nativeQuery = true)
    List<StudentClassCount> findStudentClassCount();

    String QUESTION_9 = "SELECT o.ad AS ogrenci_adi, o.soyad AS ogrenci_soyadi, COUNT(i.kitapno) AS okudugu_kitap_sayisi FROM ogrenci oLEFT JOIN islem i ON o.ogrno = i.ogrno GROUP BY o.ad, o.soyad ORDER BY okudugu_kitap_sayisi DESC;\n";
    @Query(value = QUESTION_9, nativeQuery = true)
    List<StudentNameSurnameCount> findStudentNameSurnameCount();
}

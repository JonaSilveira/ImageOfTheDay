package br.com.astronaomia.astronaomia.repository;

import br.com.astronaomia.astronaomia.models.ImageOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ImageOfTheDayRepository extends JpaRepository<ImageOfTheDay, String> {
    ImageOfTheDay findByDate(String date);
}

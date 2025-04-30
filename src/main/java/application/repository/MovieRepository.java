package application.repository;

import application.model.Movie;
import application.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findById(Integer id);
}

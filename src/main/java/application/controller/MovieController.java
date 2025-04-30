package application.controller;

import application.model.Movie;
import application.model.Review;
import application.model.User;
import application.repository.MovieRepository;
import application.repository.ReviewRepository;
import application.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    private User user;

    @GetMapping("/movies/{id}")
    public String movieDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
        this.user = (User) session.getAttribute("loggedInUser");
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
            List<Review> reviews = reviewRepository.findByMovieId(id);
            model.addAttribute("reviews", reviews);
            model.addAttribute("reviewForm", new Review());
            model.addAttribute("user", user);
            return "movie-detail";
        } else {
            return "redirect:/home";
        }
    }

    @PostMapping("/movies/{id}/reviews")
    public String addReview(@PathVariable("id") Integer movieId, @ModelAttribute("reviewForm") Review reviewForm, Model model, HttpSession session) {
        Movie movie = movieRepository.findById(movieId);
        if (movie == null) {
            return "redirect:/movies";
        }
        Review review = new Review();
        review.setMovieId(movieId);
        review.setRating(reviewForm.getRating());
        review.setComment(reviewForm.getComment());
        review.setUser(user);
        reviewRepository.save(review);
        model.addAttribute("user", user);
        return "redirect:/movies/" + movieId;
    }
}

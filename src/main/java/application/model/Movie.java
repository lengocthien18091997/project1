package application.model;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {
    private String name;
    private String description;
    private String image_url;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Getters & setters
    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() { return  image_url; }

    public void setImage_url(String image_url) { this.image_url = image_url; }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    public int getId() {
        return id;
    }
}

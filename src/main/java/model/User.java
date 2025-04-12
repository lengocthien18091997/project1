package model;
import jakarta.persistence.*;

@Entity
@Table(name = "user") // hoặc tên table bạn dùng
public class User {
	private String username;
    private String password;
    private Long id;

    // Getters & setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}

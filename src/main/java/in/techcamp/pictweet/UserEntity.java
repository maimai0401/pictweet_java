package in.techcamp.pictweet;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
}
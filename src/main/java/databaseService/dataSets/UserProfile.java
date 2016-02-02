package databaseService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by stalker on 28.01.16.
 */

@Entity
@Table(name="users")
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name="login")
    private String login;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public UserProfile(){

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}

package databaseService.dataSets;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by stalker on 02.02.16.
 */
@Entity
@Table(name="sessions")
public class UserSession implements Serializable {
    private static final long serialVersionUID = -8756689714326132798L;


    @Column(name="login")
    private String login;

    @Id
    @Column(name="session")
    private String session;

    public String getLogin(){
        return login;
    }

    public String getSession(){
        return session;
    }

    public void setSession(String session){
        this.session = session;
    }


    public UserSession(){

    }

    public UserSession(String session,String login){
        this.login = login;
        this.session = session;
    }

}

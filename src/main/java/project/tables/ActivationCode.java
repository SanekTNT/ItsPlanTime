package project.tables;

import javax.persistence.*;

@Entity
@Table(name = "activation_codes")
public class ActivationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activation_code_id")
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "activation_code")
    private String code;

    ActivationCode(){}

    public ActivationCode(User user, String code){
        this.user = user;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String activationCode) {
        this.code = activationCode;
    }

}

package model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ClientAccount {
    private int accID;
    private String email;
    private String pass;
    private boolean status;

    public ClientAccount(int accID, String email, String pass) {
        this.accID = accID;
        this.email = email;
        this.pass = pass;
    }
    
}

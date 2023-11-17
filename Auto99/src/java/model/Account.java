package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {

    private int accID;
    private String accName;
    private String password;
    private Role roleID;
    private String email;

    public Account(int accID) {
        this.accID = accID;
    }

    public Account(int accID, String accName) {
        this.accID = accID;
        this.accName = accName;
    }
    
}

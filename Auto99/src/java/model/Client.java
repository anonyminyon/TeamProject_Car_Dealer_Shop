package model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Client {

    private int clientID;
    private String clientName;
    private String email;
    private String phoneNumber;
    private String dateOfBrith;
    private boolean gender; 
    private String noID;
    
    public Client(int clientID){
        this.clientID = clientID;
    }

    public Client(int clientID, String clientName) {
        this.clientID = clientID;
        this.clientName = clientName;
    }
    
}

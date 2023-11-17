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
public class Role {

    private int roleID;
    private String roleName;

    public Role(int roleID) {
        this.roleID = roleID;
    }

    
    
}

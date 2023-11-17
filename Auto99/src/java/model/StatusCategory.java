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

public class StatusCategory {
    private int statusID;
    private String statusName;

    public StatusCategory(String statusName) {
        this.statusName = statusName;
    }
    public StatusCategory(int statusID) {
        this.statusID = statusID;
    }
}

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
public class CompanyInfo {
    private String name;
    private String businessCode;
    private String address;
    private String landlineNumber;
    private String email;
    private String hotlineDV;
    private String hotlineCSKH;
    private String website;
}

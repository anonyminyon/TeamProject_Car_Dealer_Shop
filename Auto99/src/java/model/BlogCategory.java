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

public class BlogCategory {

    private int blogCategoryID;
    private String blogCategory;

    public BlogCategory(int blogCategoryID) {
        this.blogCategoryID = blogCategoryID;
    }
}

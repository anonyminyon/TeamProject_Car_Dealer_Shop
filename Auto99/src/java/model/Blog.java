package model;

import java.sql.Date;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Blog {

    private int blogID;
    private BlogCategory blogCategoryID;
    private String title;
    private String description;
    private String content;
    private LocalDateTime createdOn;
    private Account createdBy;
    private LocalDateTime modifiedOn;
    private Account modifiedBy;
    private boolean status;
    private String blogIMG;
    private int parentID;
    private String url;

    public Blog(int blogID, BlogCategory blogCategoryID, String title, String description, LocalDateTime createdOn, String blogIMG) {
        this.blogID = blogID;
        this.blogCategoryID = blogCategoryID;
        this.title = title;
        this.description = description;
        this.createdOn = createdOn;
        this.blogIMG = blogIMG;
    }

    public Blog(int blogID, BlogCategory blogCategoryID, String title, String description, LocalDateTime createdOn, String blogIMG, int parentID) {
        this.blogID = blogID;
        this.blogCategoryID = blogCategoryID;
        this.title = title;
        this.description = description;
        this.createdOn = createdOn;
        this.blogIMG = blogIMG;
        this.parentID = parentID;
    }

    public Blog(int blogID, BlogCategory blogCategoryID, String title, String description, String content, LocalDateTime createdOn, Account createdBy, Account modifiedBy, boolean status, String blogIMG, int parentID, String url) {
        this.blogID = blogID;
        this.blogCategoryID = blogCategoryID;
        this.title = title;
        this.description = description;
        this.content = content;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.status = status;
        this.blogIMG = blogIMG;
        this.parentID = parentID;
        this.url = url;
    }



    public Blog(BlogCategory blogCategoryID, String title, LocalDateTime createdOn, int parentID, String blogIMG) {
        this.blogCategoryID = blogCategoryID;
        this.title = title;
        this.createdOn = createdOn;
        this.parentID = parentID;
        this.blogIMG = blogIMG;
    }

    public Blog(int blogID, BlogCategory blogCategoryID, String title, LocalDateTime createdOn, int parentID, String blogIMG) {
        this.blogID = blogID;
        this.blogCategoryID = blogCategoryID;
        this.title = title;
        this.createdOn = createdOn;
        this.parentID = parentID;
        this.blogIMG = blogIMG;
    }

    public Blog(String title, int parentID) {
        this.title = title;
        this.parentID = parentID;
    }

    public Blog(String content, String title, int parentID) {
        this.title = title;
        this.content = content;
        this.parentID = parentID;
    }

}

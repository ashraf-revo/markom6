package org.revo.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.revo.Domain.base.AbstractUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Created by ashraf on 30/08/16.
 */
@Document
//@Entity
public class User extends AbstractUser {
    @Id
    private String id;
    @NotBlank
    @Size(min = 3, max = 15, message = "size between 3 and 15")
    private String name;
    @NotBlank
    @Indexed(unique = true)
    private String username;
    @NotBlank
    @Indexed(unique = true)
    @Pattern(regexp = "[0-9]{5,15}", message = "enter valid phone number")
    @Size(min = 5, max = 15, message = "size between 5 and 15")
    private String phone;
    @NotBlank
    @Size(min = 3, max = 30, message = "size between 3 and 30")
    private String about;
    private Gender gender = Gender.Male;
    @NotNull
    @Past
    private Date date;
    @NotBlank
    @Size(min = 5, max = 60, message = "size between 5 and 60")
    @JsonProperty(access = WRITE_ONLY)
    private String password;
    private String image = "http://www.entando.com/portal/resources/cms/images/feature-user-management_d8.png";
    @Transient
    private String file;


    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAbout() {
        return about;
    }

    public User setAbout(String about) {
        this.about = about;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public User setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public User setDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getImage() {
        return image;
    }

    public User setImage(String image) {
        this.image = image;
        return this;
    }

    public String getFile() {
        return file;
    }

    public User setFile(String file) {
        this.file = file;
        return this;
    }
}
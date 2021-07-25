package et.debran.debranauth.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	private String sub;
	
	private String name;
	
	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	@NotBlank
	@Size(max=64)
	@JsonIgnore
	private String password;
	private String imageUrl;
	private UserType userType;

	@ManyToMany(fetch =FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinTable(
        name = "user_roles", 
        joinColumns = { @JoinColumn(name = "user_id" ,referencedColumnName = "id",
                nullable = false, updatable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "role_id" ,referencedColumnName = "id",
                nullable = false, updatable = false) }
    )
	@Fetch(value=FetchMode.SELECT)
	private List<DebranRole> roles= new ArrayList<>();;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<DebranRole> getRoles() {
		return roles;
	}
	public void setRoles(List<DebranRole> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", sub=" + sub + ", name=" + name + ", username=" + username + ", email=" + email
				+ ", imageUrl=" + imageUrl + ", userType=" + userType ;
	}
	
	
	
}

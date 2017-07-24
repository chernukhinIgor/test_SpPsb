package hello.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

	@NotNull
	@Size(min=2, max=30)
    private String name;

	@NotNull
	@Size(min=2, max=30)
    private String email;

	public User(){};

	public User(String name, String email){
	    this.name=name;
	    this.email=email;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

    public String toString() {
        return "User(Name: " + this.name + ", Email: " + this.email + ")";
    }
    
}


package object;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name ="person")
public class Person {
	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "personid",unique = true)
	private int ID;
	@Column(name = "firstname")
	private String name;
	@Column(name = "lastname")
	private String lname;
	@Column
	private String job;
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "person")
	private List<Child> childs;

	private Set<Project> projects=new HashSet<Project>();

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

    public List<Child> getChilds() {
        return childs;
    }

    public void setChilds(List<Child> childs) {
        this.childs = childs;
    }
}

package org.waddys.xcloud.project.po.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.waddys.xcloud.db.EntityBase;
import org.waddys.xcloud.user.po.entity.UserE;
import org.waddys.xcloud.vm.po.entity.VmHostE;

@Entity
@Table(name = "project")
public class ProjectE extends EntityBase{
    /**
     * 
     */
    private static final long serialVersionUID = -8942275116405514036L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(nullable = false, unique = true, length = 32)
    private String id;

    @Column(unique = true, length = 128)
    private String name;

    @Column(length = 512)
    private String description;

    @Column(name = "org_id", nullable = false, length = 32)
    private String orgId;

    // bi-directional many-to-many association to UserE
//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_user", joinColumns = {
            @JoinColumn(name = "project_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id") })
    private Set<UserE> users = new HashSet<UserE>();
    
//    @Transient
    @OneToMany(mappedBy="project",cascade={CascadeType.PERSIST})
    private Set<VmHostE> vms = new HashSet<VmHostE>();

    @Column(name = "create_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(length = 1, nullable = false)
    private String status;

    public ProjectE() {

    }

    public ProjectE(String name, String desc, String orgId, Date create_time, String status) {
        super();
        this.name = name;
        this.description = desc;
        this.orgId = orgId;
        this.createTime = create_time;
        this.status = status;
    }

    public ProjectE(String id, String name, String desc, String orgId, Date createTime, String status) {
        super();
        this.id = id;
        this.name = name;
        this.description = desc;
        this.orgId = orgId;
        this.createTime = createTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<UserE> getUsers() {
        return users;
    }

    public void setUsers(Set<UserE> users) {
        this.users = users;
    }
    
    public Set<VmHostE> getVms() {
		return vms;
	}

	public void setVms(Set<VmHostE> vms) {
		this.vms = vms;
	}
	
	public void addVm(VmHostE vm){
		vm.setProject(this);
		this.vms.add(vm);
	}

	@Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "{id:"+this.id+",users:"+users.size()+"}";
    }

}

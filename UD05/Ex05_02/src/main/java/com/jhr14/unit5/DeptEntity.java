package com.jhr14.unit5;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dept", schema = "public", catalog = "employees")
public class DeptEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "deptno", nullable = false)
    private int deptno;
    @Basic
    @Column(name = "dname", nullable = true, length = 14)
    private String dname;
    @Basic
    @Column(name = "loc", nullable = true, length = 13)
    private String loc;
    @OneToMany(mappedBy = "department")
    private List<EmployeeEntity> employeeList;

    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeptEntity that = (DeptEntity) o;

        if (deptno != that.deptno) return false;
        if (dname != null ? !dname.equals(that.dname) : that.dname != null) return false;
        if (loc != null ? !loc.equals(that.loc) : that.loc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = deptno;
        result = 31 * result + (dname != null ? dname.hashCode() : 0);
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        return result;
    }

    public List<EmployeeEntity> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeEntity> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "Deptno: " + getDeptno() + " Dname: " + getDname() + " Loc: " + getLoc();
    }
}

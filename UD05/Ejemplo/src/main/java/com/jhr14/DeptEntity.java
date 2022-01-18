package com.jhr14;

import java.util.Objects;
import java.util.Set;

public class DeptEntity {
    private int deptno;
    private String dname;
    private String loc;
    private Set<EmployeeEntity> employees;

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
        return deptno == that.deptno && Objects.equals(dname, that.dname) && Objects.equals(loc, that.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptno, dname, loc);
    }

    public Set<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeEntity> employees) {
        this.employees = employees;
    }
}

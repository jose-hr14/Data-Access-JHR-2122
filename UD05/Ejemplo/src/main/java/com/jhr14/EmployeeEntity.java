package com.jhr14;

import java.util.Objects;

public class EmployeeEntity {
    private int empno;
    private String ename;
    private String job;
    private DeptEntity deptno;

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return empno == that.empno && Objects.equals(ename, that.ename) && Objects.equals(job, that.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empno, ename, job);
    }

    public DeptEntity getDeptno() {
        return deptno;
    }

    public void setDeptno(DeptEntity deptno) {
        this.deptno = deptno;
    }
}

package com.example.springexample;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee", schema = "public", catalog = "employees")
public class EmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "empno", nullable = false)
    private int empno;
    @Basic
    @Column(name = "ename", nullable = true, length = 10)
    private String ename;
    @Basic
    @Column(name = "job", nullable = true, length = 9)
    private String job;
    @Basic
    @Column(name = "deptno", nullable = true)
    private Integer deptno;
    @ManyToOne
    @JoinColumn(name = "deptno", referencedColumnName = "deptno")
    private DeptEntity department;

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

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return empno == that.empno && Objects.equals(ename, that.ename) && Objects.equals(job, that.job) && Objects.equals(deptno, that.deptno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empno, ename, job, deptno);
    }

    public DeptEntity getDepartment() {
        return department;
    }

    public void setDepartment(DeptEntity department) {
        this.department = department;
    }
}

package com.jhr14.unit5;

import javax.persistence.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeEntity that = (EmployeeEntity) o;

        if (empno != that.empno) return false;
        if (ename != null ? !ename.equals(that.ename) : that.ename != null) return false;
        if (job != null ? !job.equals(that.job) : that.job != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = empno;
        result = 31 * result + (ename != null ? ename.hashCode() : 0);
        result = 31 * result + (job != null ? job.hashCode() : 0);
        return result;
    }

    public DeptEntity getDepartment() {
        return department;
    }

    public void setDepartment(DeptEntity department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Empno: " + getEmpno() + " Ename: " + getEname() + " Job: " + getJob() + " Deptno: "
                + department.getDeptno();
    }
}

package com.developerstack.edumanage.model;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;

public class Intake {
    private String intakeId;
    private Date startDate;
    private String intakeName; // summer,winter,
    private String programId; // programId eerathaala kattayama intake ikku mundhi program da data va set panna veandum
    private Boolean intakeCompleteness; // radio btn

    public Intake() {
    }

    public String getIntakeId() {
        return intakeId;
    }

    public void setIntakeId(String intakeId) {
        this.intakeId = intakeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getIntakeName() {
        return intakeName;
    }

    public void setIntakeName(String intakeName) {
        this.intakeName = intakeName;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public Boolean getIntakeCompleteness() {
        return intakeCompleteness;
    }

    public void setIntakeCompleteness(Boolean intakeCompleteness) {
        this.intakeCompleteness = intakeCompleteness;
    }
    // izula best practise ennenda intake aahakole complete ille so azu default false aakkanum
    // so azu ui la kaattatheawallandu sir senna object seyyakola false indu kudu


    @Override
    public String toString() {
        return "Intake{" +
                "intakeId='" + intakeId + '\'' +
                ", startDate=" + startDate +
                ", intakeName='" + intakeName + '\'' +
                ", programId='" + programId + '\'' +
                ", intakeCompleteness=" + intakeCompleteness +
                '}';
    }
}

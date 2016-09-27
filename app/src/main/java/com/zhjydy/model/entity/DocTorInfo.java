package com.zhjydy.model.entity;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class DocTorInfo {
    String photoUrl;
    String name;
    String hospital;
    String office;
    String profess;
    String special;
    String score;
    String star;


    public DocTorInfo() {

    }

    public DocTorInfo(String photoUrl, String name, String hospital, String office, String profess, String special, String score, String star) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.hospital = hospital;
        this.office = office;
        this.profess = profess;
        this.special = special;
        this.score = score;
        this.star = star;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getProfess() {
        return profess;
    }

    public void setProfess(String profess) {
        this.profess = profess;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}

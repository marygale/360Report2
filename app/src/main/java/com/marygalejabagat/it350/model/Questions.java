package com.marygalejabagat.it350.model;

import com.marygalejabagat.it350.model.Dimension;

public class Questions {
    private int id, surveyId;
    private int dimension;
    private int with_options;
    private int is_calculating;
    private String created;
    private String name;
    private String modified;
    private int is_deleted;
    private String dimensionName;
    private boolean isSelected;


    public Questions(){

    }

    public Questions(int id, String name, int dimension, int with_options, int is_calculating, String created, String modified, String dimension_name, boolean isSelected, int surveyId){
        this.id = id;
        this.name = name;
        this.dimension = dimension;
        this.with_options = with_options;
        this.is_calculating = is_calculating;
        this.created = created;
        this.modified = modified;
        this.dimensionName = dimension_name;
        this.isSelected = isSelected;
        this.surveyId = surveyId;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getWith_options() {
        return with_options;
    }

    public void setWith_options(int with_options) {
        this.with_options = with_options;
    }

    public int getIs_calculating() {
        return is_calculating;
    }

    public void setIs_calculating(int is_calculating) {
        this.is_calculating = is_calculating;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

     public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int id) {
        this.surveyId = id;
    }
}

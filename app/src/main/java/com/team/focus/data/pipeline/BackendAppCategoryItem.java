package com.team.focus.data.pipeline;

public class BackendAppCategoryItem {

    private final String appId;
    private final String category;

    public BackendAppCategoryItem(String id, String cat)	{
        appId = id;
        category = cat;
    }

    public String getAppId()	{
        return appId;
    }

    public String getCategory()	{
        return category;
    }

    public String toString() {
        return "<" + appId + ", " + category + ">";
    }

}

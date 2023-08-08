package org.example.Model;

public class Issue {
    int id;
    String title;
    String description;
    String tag;
    IssueType type;
    IssuePriority priority;
    Integer user_id;
    int project_id;
    String cDate;


    public Issue(int id,String title, String description, String tag, IssueType type, IssuePriority priority, Integer user_id, int project_id, String cDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.type = type;
        this.priority = priority;
        this.user_id = user_id;
        this.project_id = project_id;
        this.cDate = cDate;
    }

    public Issue(String title, String description, String tag, IssueType type, IssuePriority priority, Integer user_id, int project_id, String cDate) {
        this(-1,title,description,tag,type,priority,user_id,project_id,cDate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getCDate() {
        return cDate;
    }

    public void setCDate(String cDate) {
        this.cDate = cDate;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", type=" + type +
                ", priority=" + priority +
                ", user_id=" + user_id +
                ", project_id=" + project_id +
                ", cDate='" + cDate + '\'' +
                '}';
    }
}

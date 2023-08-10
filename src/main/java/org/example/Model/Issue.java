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
    IssueState state;

    public IssueState getState() {
        return state;
    }

    public void setState(IssueState state) {
        this.state = state;
    }

    public Issue(int id, String title, String description, String tag, IssueType type, IssuePriority priority, Integer user_id, int project_id, String cDate, IssueState state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.type = type;
        this.priority = priority;
        this.user_id = user_id;
        this.project_id = project_id;
        this.cDate = cDate;
        this.state = state;
    }

    public Issue(String title, String description, String tag, IssueType type, IssuePriority priority, Integer user_id, int project_id, String cDate, IssueState issieState) {
        this(-1,title,description,tag,type,priority,user_id,project_id,cDate, issieState);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public IssueType getType() {
        return type;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public String getCDate() {
        return cDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setcDate(String cDate) {
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
                ", state='" + state + '\'' +
                '}';
    }
}

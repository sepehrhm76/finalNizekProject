package org.example.Conroller;

import org.example.Database.issue.IssueRepository;
import org.example.Model.Issue;
import org.example.Model.IssuePriority;
import org.example.Model.IssueState;
import org.example.Model.IssueType;


import java.util.List;

public class IssueController {

    private final IssueRepository issueRepository = new IssueRepository();

    public IssueController() {
    }

    public boolean hasAnyUser() {
        return issueRepository.hasAnyIssue();
    }

    public void addIssue(String title, String description, String tag, IssueType type, IssuePriority priority, Integer user_id, int project_id, String cDate, IssueState issueState){
        Issue issue = new Issue(title, description, tag, type, priority, user_id, project_id, cDate, issueState);
        issueRepository.create(issue);
    }

    public void updateIssue(int id, String title, String description, String tag, IssueType type, IssuePriority priority, Integer user_id, int project_id, String cDate, IssueState state){
        Issue issue = new Issue(title, description, tag, type, priority, user_id, project_id, cDate, state);
        issueRepository.update(id, issue);
    }

    public void removeIssue(Issue issue) {
        if (issue != null) {
            issueRepository.delete(issue);
        }
    }

    public List<Issue> getIssuesByProjectId(int projectId) {
        return issueRepository.getIssueByProjectId(projectId);
    }
}

package org.example.Conroller;

import org.example.Database.issue.IssueRepository;
import org.example.Model.Issue;
import org.example.Model.IssuePriority;
import org.example.Model.IssueType;
import org.example.Model.User;

import java.util.List;

public class IssueController {
    private IssueRepository issueRepository = new IssueRepository();

    public IssueController() {
    }

    public boolean hasAnyUser() {
        return issueRepository.hasAnyIssue();
    }

    public void addIssue(String title, String description, String tag, IssueType type, IssuePriority priority, int user_id, int project_id, String cDate){
        Issue issue = new Issue(title, description, tag, type, priority, user_id, project_id, cDate);
        issueRepository.create(issue);
    }

    public void updateIssue(int id, String title, String description, String tag, IssueType type, IssuePriority priority, int user_id, int project_id, String cDate){
        Issue issue = new Issue(title, description, tag, type, priority, user_id, project_id, cDate);
        issueRepository.update(id, issue);
    }

    public void removeIssue(Issue issue) {
        if (issue != null) {
            issueRepository.delete(issue);
        }
    }

    public List<Issue> getAllIssues() {
        return issueRepository.getAll();
    }
}

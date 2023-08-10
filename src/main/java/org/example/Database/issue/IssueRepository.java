package org.example.Database.issue;

import org.example.Database.SQLiteWrapper;
import org.example.Log.Logger;
import org.example.Model.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class IssueRepository {
    private static final String TABLE_NAME = "issue";

    private final SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean create(Issue issue) {
        String query = String.format("INSERT INTO %s " +
                        "(%s, %s, %s, %s, %s, %s, %s) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)",
                TABLE_NAME,
                IssueColumns.title,
                IssueColumns.description,
                IssueColumns.tag,
                IssueColumns.type,
                IssueColumns.priority,
                IssueColumns.user_id,
                IssueColumns.project_id
        );

        int rowsAffected = sqlite.executeUpdate(query,
                issue.getTitle(),
                issue.getDescription(),
                issue.getTag(),
                issue.getType(),
                issue.getPriority(),
                issue.getUser_id(),
                issue.getProject_id()
        );
        return rowsAffected > 0;
    }

    public boolean update(int id, Issue issue) {
        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME,
                IssueColumns.title,
                IssueColumns.description,
                IssueColumns.tag,
                IssueColumns.type,
                IssueColumns.priority,
                IssueColumns.user_id,
                IssueColumns.project_id,
                IssueColumns.id
        );

        int rowsAffected = sqlite.executeUpdate(query,
                issue.getTitle(),
                issue.getDescription(),
                issue.getTag(),
                issue.getType(),
                issue.getPriority(),
                issue.getUser_id(),
                issue.getProject_id(),
                id);
        return rowsAffected > 0;
    }

    public void delete(Issue issue) {
        delete(issue.getId());
    }

    public void delete(int issueId) {
        sqlite.executeUpdate(
                String.format("DELETE FROM %s WHERE %s = ?;", TABLE_NAME, IssueColumns.id), issueId
        );
    }

    public Issue get(int issueId) {

        ResultSet result = sqlite.executeQuery(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, IssueColumns.id), issueId);
        try {
            if (result.next()) {
                return this.createIssueFromResultSet(result);
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return null;
    }

    public List<Issue> getIssuesWithAndFilter(int projectId, int userId, IssuePriority priority) {
        ArrayList<Issue> list = new ArrayList<>();

        String query = String.format("SELECT * FROM %s WHERE %s=? AND %s=? AND %s=?", TABLE_NAME, IssueColumns.project_id, IssueColumns.user_id, IssueColumns.priority);
        ResultSet result = sqlite.executeQuery(query, projectId, userId, priority.toString());
        try {
            while (result.next()) {
                list.add(this.createIssueFromResultSet(result));
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }

    public List<Issue> getIssuesWithOrFilter(int projectId, int userId, IssuePriority priority) {
        ArrayList<Issue> list = new ArrayList<>();

        String query = String.format("SELECT * FROM %s WHERE %s=? AND %s=? OR %s=?", TABLE_NAME, IssueColumns.project_id, IssueColumns.user_id, IssueColumns.priority);
        ResultSet result = sqlite.executeQuery(query, projectId, userId, priority.toString());
        try {
            while (result.next()) {
                list.add(this.createIssueFromResultSet(result));
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }

    public List<Issue> getIssueByProjectId(int projectId) {
        ArrayList<Issue> list = new ArrayList<>();

        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, IssueColumns.project_id);
        ResultSet result = sqlite.executeQuery(query, projectId);
        try {
            while (result.next()) {
                list.add(this.createIssueFromResultSet(result));
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }

    public boolean hasAnyIssue() {
        int count = sqlite.executeUpdate(String.format("SELECT COUNT(*) FROM %s", TABLE_NAME));
        return count > 0;
    }

    private Issue createIssueFromResultSet(ResultSet result) throws Exception {
        int id = result.getInt(IssueColumns.id.toString());
        String title = result.getString(IssueColumns.title.toString());
        String description = result.getString(IssueColumns.description.toString());
        String tag = result.getString(IssueColumns.tag.toString());
        IssueType type = IssueType.fromString(result.getString(IssueColumns.type.toString()));
        IssuePriority priority = IssuePriority.fromString(result.getString(IssueColumns.priority.toString()));
        int user_id = result.getInt(IssueColumns.user_id.toString());
        int project_id = result.getInt(IssueColumns.project_id.toString());
        String cDate = result.getString(IssueColumns.cDate.toString());
        return new Issue(id, title, description, tag, type, priority, user_id,project_id,cDate);
    }
}



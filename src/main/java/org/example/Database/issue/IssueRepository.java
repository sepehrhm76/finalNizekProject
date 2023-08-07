package org.example.Database.issue;

import org.example.Database.SQLiteWrapper;
import org.example.Log.Logger;
import org.example.Model.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class IssueRepository {
    private static final String TABLE_NAME = "issue";

    private SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean create(Issue issue) {
        String query = String.format("INSERT INTO %s " +
                        "(%s, %s, %s, %s, %s, %s, %s, %s) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                TABLE_NAME,
                IssueColumns.title.toString(),
                IssueColumns.description.toString(),
                IssueColumns.tag.toString(),
                IssueColumns.type.toString(),
                IssueColumns.priority.toString(),
                IssueColumns.user_id.toString(),
                IssueColumns.project_id.toString(),
                IssueColumns.cDate.toString()
        );

        int rowsAffected = sqlite.executeUpdate(query,
                issue.getTitle(),
                issue.getDescription(),
                issue.getTag(),
                issue.getType(),
                issue.getPriority(),
                issue.getUser_id(),
                issue.getProject_id(),
                issue.getCDate()
        );
        return rowsAffected > 0;
    }

    public boolean update(int id, Issue issue) {
        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME,
                IssueColumns.title.toString(),
                IssueColumns.description.toString(),
                IssueColumns.tag.toString(),
                IssueColumns.type.toString(),
                IssueColumns.priority.toString(),
                IssueColumns.user_id.toString(),
                IssueColumns.project_id.toString(),
                IssueColumns.cDate.toString(),
                IssueColumns.id.toString()
        );

        int rowsAffected = sqlite.executeUpdate(query,
                issue.getTitle(),
                issue.getDescription(),
                issue.getTag(),
                issue.getType(),
                issue.getPriority(),
                issue.getUser_id(),
                issue.getProject_id(),
                issue.getCDate(),
                id);
        return rowsAffected > 0;
    }

    public void delete(Issue issue) {
        delete(issue.getId());
    }

    public void delete(int issueId) {
        sqlite.executeUpdate(
                String.format("DELETE FROM %s WHERE %s = ?;", TABLE_NAME, IssueColumns.id.toString()), issueId
        );
    }

    public Issue get(int issueId) {

        ResultSet result = sqlite.executeQuery(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, IssueColumns.id.toString()), issueId);
        try {
            if (result.next()) {
                return this.createIssueFromResultSet(result);
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return null;
    }

    public List<Issue> getAll() {
        ArrayList<Issue> list = new ArrayList<>();

        String query = String.format("SELECT * FROM %s", TABLE_NAME);
        ResultSet result = sqlite.executeQuery(query);
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



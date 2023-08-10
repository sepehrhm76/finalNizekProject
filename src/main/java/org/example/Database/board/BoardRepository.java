package org.example.Database.board;

import org.example.Database.SQLiteWrapper;
import org.example.Database.issue.IssueColumns;
import org.example.Log.Logger;
import org.example.Model.Board;
import org.example.Model.Issue;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {

    private static final String TABLE_NAME = "board";

    private final SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean create(Board board) {
        String query = String.format("INSERT INTO %s " +
                        "(%s, %s) " +
                        "VALUES (?, ?)",
                TABLE_NAME,
                BoardColumns.name,
                BoardColumns.project_id
        );

        int rowsAffected = sqlite.executeUpdate(query,
                board.getName(),
                board.getProject_id()
                );
        return rowsAffected > 0;
    }

    public boolean update(int id, Board board) {
        String query = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                TABLE_NAME,
                BoardColumns.name,
                BoardColumns.project_id,
                BoardColumns.id
        );

        int rowsAffected = sqlite.executeUpdate(query,
                board.getName(),
                board.getProject_id(),
                id);
        return rowsAffected > 0;
    }

    public void delete(Board board) {
        delete(board.getId());
    }

    public void delete(int boardId) {
        sqlite.executeUpdate(
                String.format("DELETE FROM %s WHERE %s = ?;", TABLE_NAME, BoardColumns.id), boardId
        );
    }

    public Board get(int boardId) {

        ResultSet result = sqlite.executeQuery(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, BoardColumns.id), boardId);
        try {
            if (result.next()) {
                return this.createBoardFromResultSet(result);
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return null;
    }

    public List<Board> getBoardByProjectId(int projectId) {
        ArrayList<Board> list = new ArrayList<>();

        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, BoardColumns.project_id);
        ResultSet result = sqlite.executeQuery(query, projectId);
        try {
            while (result.next()) {
                list.add(this.createBoardFromResultSet(result));
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return list;
    }

    public boolean hasAnyBoard() {
        int count = sqlite.executeUpdate(String.format("SELECT COUNT(*) FROM %s",TABLE_NAME));
        return count > 0;
    }

    private Board createBoardFromResultSet(ResultSet result) throws Exception {
        int id = result.getInt(BoardColumns.id.toString());
        String name = result.getString(BoardColumns.name.toString());
        int project_id = result.getInt(BoardColumns.project_id.toString());
        return new Board(id, name, project_id);
    }
}

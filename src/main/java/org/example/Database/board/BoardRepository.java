package org.example.Database.board;

import org.example.Database.SQLiteWrapper;
import org.example.Log.Logger;
import org.example.Model.Board;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
    private static final String TABLE_NAME = "board";


    private SQLiteWrapper sqlite = SQLiteWrapper.getInstance();

    public boolean create(Board board) {
        String query = String.format("INSERT INTO %s " +
                        "(%s) " +
                        "VALUES (?)",
                TABLE_NAME,
                BoardColumns.name.toString()
        );

        int rowsAffected = sqlite.executeUpdate(query,
                board.getName());
        return rowsAffected > 0;
    }

    public boolean update(int id, Board board) {
        String query = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                TABLE_NAME,
                BoardColumns.name.toString(),
                BoardColumns.id.toString()
        );

        int rowsAffected = sqlite.executeUpdate(query,
                board.getName(),
                id);
        return rowsAffected > 0;
    }

    public void delete(Board board) {
        delete(board.getId());
    }

    public void delete(int boardId) {
        sqlite.executeUpdate(
                String.format("DELETE FROM %s WHERE %s = ?;", TABLE_NAME, BoardColumns.id.toString()), boardId
        );
    }

    public Board get(int boardId) {

        ResultSet result = sqlite.executeQuery(String.format("SELECT * FROM %s WHERE %s = ?", TABLE_NAME, BoardColumns.id.toString()), boardId);
        try {
            if (result.next()) {
                return this.createBoardFromResultSet(result);
            }
        } catch (Exception e) {
            Logger.getInstance().logError("Error reading ResultSet: " + e.getMessage());
        }
        return null;
    }

    public List<Board> getAll() {
        ArrayList<Board> list = new ArrayList<>();

        String query =String.format("SELECT * FROM %s", TABLE_NAME) ;
        ResultSet result = sqlite.executeQuery(query);
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
        return new Board(id, name);
    }
}

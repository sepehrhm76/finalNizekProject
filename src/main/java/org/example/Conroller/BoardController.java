package org.example.Conroller;

import org.example.Database.board.BoardRepository;
import org.example.Model.Board;
import org.example.Model.Issue;

import java.util.List;

public class BoardController {
    private static BoardController instance = null;
    private final BoardRepository boardRepository = new BoardRepository();

    public BoardController() {
    }

    public static BoardController getInstance() {
        if (instance == null)
            instance = new BoardController();
        return instance;
    }

    public boolean hasAnyBoard() {
        return boardRepository.hasAnyBoard();
    }

    public void addBoard(String name, int project_id) {
        Board board = new Board(name, project_id);
        boardRepository.create(board);
    }

    public void updateBoard(int id, String name, int project_id) {
        Board board = new Board(name, project_id);
        boardRepository.update(id, board);
    }

    public void removeBoard(Board board) {
        if (board != null) {
            boardRepository.delete(board);
        }
    }

    public List<Board> getBoardsByProjectId(int projectId) {
        return boardRepository.getBoardByProjectId(projectId);
    }

}

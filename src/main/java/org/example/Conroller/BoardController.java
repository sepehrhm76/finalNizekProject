package org.example.Conroller;

import org.example.Database.board.BoardRepository;
import org.example.Model.Board;
import java.util.List;

public class BoardController {
    private static BoardController instance = null;
    private BoardRepository boardRepository = new BoardRepository();

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

    public void addBoard(String name) {
        Board board = new Board(name);
        boardRepository.create(board);
    }

    public void updateBoard(int id, String name) {
        Board board = new Board(name);
        boardRepository.update(id, board);
    }

    public void removeBoard(Board board) {
        if (board != null) {
            boardRepository.delete(board);
        }
    }

    public List<Board> getAllBoards() {
        return boardRepository.getAll();
    }

}

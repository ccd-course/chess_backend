package com.chess.backend.restController.objects;

public class ExecutedMoveObject {
    private ValidationResponse validation;
    private Object[][] chessboard;

    public ExecutedMoveObject(ValidationResponse validation, Object[][] chessboard){
        this.validation = validation;
        this.chessboard = chessboard;
    }

    public ValidationResponse getValidation() {
        return validation;
    }

    public void setValidation(ValidationResponse validation) {
        this.validation = validation;
    }

    public Object[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(Object[][] chessboard) {
        this.chessboard = chessboard;
    }
}

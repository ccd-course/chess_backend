package com.chess.backend.restController.objects;

/**
 * This class is send as a response from the {@link com.chess.backend.restController.controller.ExecutedMoveController}.
 *
 * It contains the following fields: <br>
 * {@link validation} - the validation state of the move ({@link ValidationResponse}) <br>
 * {@link chessboard} - the chessboard
 *
 * @author Hannes Stuetzer
 */
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

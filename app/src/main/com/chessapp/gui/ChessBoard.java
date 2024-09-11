package com.chessapp.gui;

import com.chessapp.api.game.GameState;
import com.chessapp.api.pieces.utils.ChessPieceColor;
import com.chessapp.gui.utils.ChessImages;
import com.chessapp.gui.utils.Constants;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 * The ChessBoard GUI, will be used to house chess pieces and
 * link up with Chess API
 */

public class ChessBoard extends Application {
    private static final int BOARD_SIZE = Constants.BOARD_SIZE;
    private static final int OFFSET = Constants.OFFSET;

    private static ArrayList<ImageView> views;
    private HashMap<ImageView, ChessPieceColor> colorMap;

    private ImageView currentPiece;
    private APILinker game;

    public static Rectangle[][] boardSquares = new Rectangle[8][8];

    private static StackPane board;

    // Some local variables used within the ChessBoard
    int playerOneVal = 0;
    int playerTwoVal = 0;
    Stage stage;

    // Used for undo
    ImageView lastMovedImage;
    double lastX;
    double lastY;

    /**
     * This is our "main" method, required by JavaFX in order to
     * run a JavaFX application. It sets up all of our internal logic
     * and links up with the API to make our Chess game functional
     *
     * @param primaryStage This is required by JavaFX to throw nodes at
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chess");
        stage = primaryStage;

        board = new StackPane();
        Canvas gameCanvas = new Canvas(BOARD_SIZE, BOARD_SIZE);
        views = new ArrayList<>();
        colorMap = new HashMap<>();

        setUpBoard();
        ChessImages images = new ChessImages();
        addPiecesToBoard(images, board, gameCanvas);

        StackPane dashboard = new StackPane();
        Canvas dashCanvas = new Canvas(BOARD_SIZE / 2, BOARD_SIZE);
        setUpDashboard(dashboard, dashCanvas);

        SplitPane root = new SplitPane();
        root.getItems().addAll(board, dashboard);
        primaryStage.setScene(new Scene(root, 3 * BOARD_SIZE / 2, BOARD_SIZE));

        game = new APILinker();
        primaryStage.show();
    }

    /**
     * Helper to set up the ChessBoard itself
     */
    private void setUpBoard() {
        double origin = - BOARD_SIZE / 2 + OFFSET / 2;
        double currX = origin;
        double currY = origin;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardSquares[i][j] = new Rectangle(currX, currY, OFFSET, OFFSET);
                if (((i + j) % 2) == 0) {
                    boardSquares[i][j] = new Rectangle(OFFSET, OFFSET, Color.TAN);
                } else {
                    boardSquares[i][j] = new Rectangle(OFFSET, OFFSET, Color.web("#8C001A"));
                }
                boardSquares[i][j].setTranslateX(currX);
                boardSquares[i][j].setTranslateY(currY);
                boardSquares[i][j].setOnMouseClicked(squareOnClickHandler);
                currX += OFFSET;
            }
            currX = origin;
            currY += OFFSET;
        }
    }

    /**
     * Helper function to add all of the pieces (after initializing)
     * to the list of pieces on the board so we can conveniently add
     * them to the canvas later
     *
     * @param images the images manager, used to load chess images
     */
    private void addPiecesToBoard(ChessImages images, StackPane board, Canvas gameCanvas) {
        initializeImageView(images.redRook, Constants.LEFT_ROOK_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        initializeImageView(images.redRook, Constants.RIGHT_ROOK_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        initializeImageView(images.redKnight, Constants.LEFT_KNIGHT_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        initializeImageView(images.redKnight, Constants.RIGHT_KNIGHT_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        initializeImageView(images.redBishop, Constants.LEFT_BISHOP_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        initializeImageView(images.redBishop, Constants.RIGHT_BISHOP_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        initializeImageView(images.redQueen, Constants.QUEEN_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        initializeImageView(images.redKing, Constants.KING_X_POS, Constants.RED_Y_POS, ChessPieceColor.RED);
        for (int i = 0; i < 8; i++) {
            initializeImageView(images.redPawn, -BOARD_SIZE / 2 + (2 * i + 1) * OFFSET / 2, Constants.RED_PAWN_Y_POS, ChessPieceColor.RED);
        }

        initializeImageView(images.whiteRook, Constants.LEFT_ROOK_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        initializeImageView(images.whiteRook, Constants.RIGHT_ROOK_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        initializeImageView(images.whiteKnight, Constants.LEFT_KNIGHT_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        initializeImageView(images.whiteKnight, Constants.RIGHT_KNIGHT_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        initializeImageView(images.whiteBishop, Constants.LEFT_BISHOP_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        initializeImageView(images.whiteBishop, Constants.RIGHT_BISHOP_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        initializeImageView(images.whiteQueen, Constants.QUEEN_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        initializeImageView(images.whiteKing, Constants.KING_X_POS, Constants.WHITE_Y_POS, ChessPieceColor.WHITE);
        for (int i = 0; i < 8; i++) {
            initializeImageView(images.whitePawn, -BOARD_SIZE / 2 + (2 * i + 1) * OFFSET / 2, Constants.WHITE_PAWN_Y_POS, ChessPieceColor.WHITE);
        }

        board.getChildren().add(gameCanvas);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board.getChildren().add(boardSquares[i][j]);
            }
        }
        for (ImageView i : views) {
            board.getChildren().add(i);
        }
    }

    /**
     * Event handler for chess pieces. When selected, we need to update the
     * current selected piece.
     */
    EventHandler<MouseEvent> imageOnMouseClickedHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            currentPiece = (ImageView) (event.getSource());
        }
    };

    /**
     * Event handler for chessboard squares. When selected, we need to move
     * the current selected piece there (if the current piece is defined)
     * and then clear the current piece so it doesn't move again after clicking
     * on another square.
     */
    EventHandler<MouseEvent> squareOnClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (currentPiece == null) {
                return;
            }

            if (game.currentTurn() != colorMap.get(currentPiece)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Not your turn");
                alert.setHeaderText("Not your turn");
                alert.setContentText("Please wait for the other player to make their move before you do.");
                alert.showAndWait();
                return;
            }

            Rectangle source = (Rectangle) event.getSource();

            double nextX = source.getTranslateX();
            double nextY = source.getTranslateY();

            try {
                lastMovedImage = currentPiece;
                lastX = currentPiece.getTranslateX();
                lastY = currentPiece.getTranslateY();

                game.tryMove(currentPiece, nextX, nextY);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Move");
                alert.setHeaderText("An invalid move was attempted");
                alert.setContentText(e.getMessage());
                alert.showAndWait();

                lastX = -1;
                lastY = -1;
                lastMovedImage = null;

                return;
            }

            GameState currState = game.checkGameConditions();

            if (currState == GameState.CHECK) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Check");
                alert.setHeaderText("The " + game.currentTurn().toString().toLowerCase() + " player can check");
                alert.showAndWait();
            }

            else if (currState == GameState.CHECKMATE) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Checkmate!");
                alert.setHeaderText("The " + game.currentTurn().toString().toLowerCase() + " player has won the game!");
                alert.showAndWait();

                currentPiece.setTranslateX(nextX);
                currentPiece.setTranslateY(nextY);
                currentPiece = null;
                winGame();
                start(stage);
                return;
            }

            else if (currState == GameState.STALEMATE) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Stalemate");
                alert.setHeaderText("The " + game.currentTurn().toString().toLowerCase() + " player is in stalemate");
                alert.setContentText("You can either forfeit or restart, but you cannot win this game.");
                alert.showAndWait();
            }

            game.changeTurn();

            currentPiece.setTranslateX(nextX);
            currentPiece.setTranslateY(nextY);
            currentPiece = null;
        }
    };

    /**
     * Helper function to create an ImageView around the image object, set its
     * x and y position, and add the click handler to let us know when a chess
     * piece has been selected
     *
     * @param currImage The image we are trying to initialize
     * @param xPos The x-position of the chess piece
     * @param yPos The y-position of the chess piece
     */
    private void initializeImageView(Image currImage, double xPos, double yPos, ChessPieceColor color) {
        ImageView imageView = new ImageView(currImage);
        imageView.setTranslateX(xPos);
        imageView.setTranslateY(yPos);
        imageView.setOnMouseClicked(imageOnMouseClickedHandler);
        views.add(imageView);
        colorMap.put(imageView, color);
    }

    /**
     * The helper for setting up our player dashboard. Sets up the dashboard canvas,
     * the labels / buttons & their event handlers, and other UI pieces of the dash
     *
     * @param dashboard the StackPane in which we are adding the canvas
     * @param dashCanvas the Canvas in which we are laying out all of our dash UI components
     */
    private void setUpDashboard(StackPane dashboard, Canvas dashCanvas) {

        // We need to do this to easily add rectangles without having to use translate
        GraphicsContext gc = dashCanvas.getGraphicsContext2D();
        gc.setFill(Color.TAN);
        gc.fillRect(0, BOARD_SIZE / 2, BOARD_SIZE, BOARD_SIZE / 2);
        gc.setFill(Color.web("#8C001A"));
        gc.fillRect(0, 0, BOARD_SIZE, BOARD_SIZE / 2);

        // Player 1 labels, scoring, and house
        Label playerOneLabel = new Label("Player 1");
        playerOneLabel.setFont(new Font("Arial", 24));
        playerOneLabel.setTextFill(Color.web("#8C001A"));
        playerOneLabel.setTranslateX(0);
        playerOneLabel.setTranslateY(BOARD_SIZE / 8);
        Rectangle playerOneScoreBox = new Rectangle(BOARD_SIZE / 5, BOARD_SIZE / 5, Color.web("#8C001A"));
        playerOneScoreBox.setTranslateX(0);
        playerOneScoreBox.setTranslateY(2.5 * BOARD_SIZE / 8);
        Label playerOneScore = new Label(playerOneVal + "");
        playerOneScore.setTextFill(Color.TAN);
        playerOneScore.setFont(new Font("Arial", 64));
        playerOneScore.setTranslateX(0);
        playerOneScore.setTranslateY(2.5 * BOARD_SIZE / 8);

        // Player two labels, scoring, and house
        Label playerTwoLabel = new Label("Player 2");
        playerTwoLabel.setFont(new Font("Arial", 24));
        playerTwoLabel.setTextFill(Color.TAN);
        playerTwoLabel.setTranslateX(0);
        playerTwoLabel.setTranslateY(-3 * BOARD_SIZE / 8);
        Rectangle playerTwoScoreBox = new Rectangle(BOARD_SIZE / 5, BOARD_SIZE / 5, Color.TAN);
        playerTwoScoreBox.setTranslateX(0);
        playerTwoScoreBox.setTranslateY(-1.5 * BOARD_SIZE / 8);
        Label playerTwoScore = new Label(playerTwoVal + "");
        playerTwoScore.setTextFill(Color.web("#8C001A"));
        playerTwoScore.setFont(new Font("Arial", 64));
        playerTwoScore.setTranslateX(0);
        playerTwoScore.setTranslateY(-BOARD_SIZE / 5.3);

        // Sets up the forfeit button
        Button forfeitButton = new Button("Forfeit");
        forfeitButton.setTranslateX(-BOARD_SIZE / 7);
        forfeitButton.setTranslateY(0);
        forfeitButton.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Forfeit");
            alert.setHeaderText("Forfeit");
            alert.setContentText(game.currentTurn().toString() + " player has forfeited the game.");
            game.changeTurn();
            winGame();
            alert.showAndWait();
            start(stage);
        });

        // Sets up the undo button
        Button undoButton = new Button("Undo");
        undoButton.setTranslateX(BOARD_SIZE / 7);
        undoButton.setTranslateY(0);
        undoButton.setOnMouseClicked(e -> {
            try {
                undoMove();
                game.changeTurn();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Undo Error");
                alert.setHeaderText("Error in Undo");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        // Sets up the restart button
        Button restartButton = new Button("Restart");
        restartButton.setTranslateX(0);
        restartButton.setTranslateY(0);
        restartButton.setOnMouseClicked(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Restart");
            alert.setHeaderText("Restart the game?");
            alert.setContentText("Confirm if you would like to restart the game");
            Optional<ButtonType> o = alert.showAndWait();
            if (o.get() == ButtonType.OK) {
                start(stage);
            }
        });

        // Finally, we add all of our child nodes to the StackPane
        dashboard.getChildren().addAll(dashCanvas,
                playerOneLabel, playerOneScoreBox, playerOneScore,
                playerTwoLabel, playerTwoScoreBox, playerTwoScore,
                forfeitButton, undoButton, restartButton);
    }


    /**
     * Pretty simple function to increment scores
     */
    private void winGame() {
        if (game.currentTurn() == ChessPieceColor.RED) {
            playerTwoVal++;
        } else {
            playerOneVal++;
        }
    }

    /**
     * We use this to link to the API Linker for undoing moves
     *
     * @throws Exception if there is an invalid previous move
     */
    private void undoMove() throws Exception {
        if (lastX == -1 || lastY == -1) {
            throw new Exception("Could not undo move");
        }
        game.undoMove(lastMovedImage.getTranslateX(), lastMovedImage.getTranslateY(), lastX, lastY);
        lastMovedImage.setTranslateX(lastX);
        lastMovedImage.setTranslateY(lastY);
    }

    /**
     * Getter for ImageViews list
     *
     * @return Our ChessPieces in ImageView form!
     */
    public static ArrayList<ImageView> getViews() {
        return views;
    }

    /**
     * Used by the API Linker to remove a chess piece from the board,
     * usually after an attack
     *
     * @param image Which we want to remove
     */
    public static void removeChessImage(ImageView image) {
        board.getChildren().remove(image);
    }

    /**
     * Don't judge a book by its cover! This little guy takes care of
     * a LOT of heavy lifting done by JavaFX in order to get everything working
     * properly. Make sure to kiss it on the feet to show ultimate respect.
     *
     * @param args Idk what these are for tbh
     */
    public static void main(String[] args) {
        launch(args);
    }
}

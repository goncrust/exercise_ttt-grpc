package pt.tecnico.ttt.server;

import io.grpc.stub.StreamObserver;
import static io.grpc.Status.INVALID_ARGUMENT;
import pt.tecnico.ttt.*;

import java.util.List;

public class TTTServiceImpl extends TTTGrpc.TTTImplBase {

	/** Game implementation. */
	private TTTGame ttt = new TTTGame();

	@Override
	public void currentBoard(CurrentBoardRequest request, StreamObserver<CurrentBoardResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		CurrentBoardResponse response = CurrentBoardResponse.newBuilder().setBoard(ttt.toString()).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	@Override
	public void play(PlayRequest request, StreamObserver<PlayResponse> responseObserver) {
		List<Integer> play = request.getPlayList();
		PlayResult result = ttt.play(play.get(0), play.get(1), play.get(2));

		// if play out of bounds, send exception
		if (result == PlayResult.OUT_OF_BOUNDS) {
			responseObserver.onError(INVALID_ARGUMENT.withDescription("Input as to be a valid position").asRuntimeException());
			return;
		}

		PlayResponse response = PlayResponse.newBuilder().setResult(result).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	@Override
	public void checkWinner(CheckWinnerRequest request, StreamObserver<CheckWinnerResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		CheckWinnerResponse response = CheckWinnerResponse.newBuilder().setWinner(ttt.checkWinner()).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	@Override
	public void waitForWinner(WaitForWinnerRequest request, StreamObserver<WaitForWinnerResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		WaitForWinnerResponse response = WaitForWinnerResponse.newBuilder().setWinner(ttt.waitForWinner()).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}
}

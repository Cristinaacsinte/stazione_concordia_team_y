package it.euris.stazioneconcordia.service.impl;

import com.google.gson.Gson;
import it.euris.stazioneconcordia.data.dto.BoardDTO;
import it.euris.stazioneconcordia.data.model.Board;
import it.euris.stazioneconcordia.data.trelloDto.BoardTrelloDTO;
import it.euris.stazioneconcordia.exception.IdMustBeNullException;
import it.euris.stazioneconcordia.exception.IdMustNotBeNullException;
import it.euris.stazioneconcordia.repository.BoardRepository;
import it.euris.stazioneconcordia.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@AllArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    BoardRepository boardRepository;

    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Override
    public Board insert(Board board) {
        if (board.getId() != null) {
            throw new IdMustBeNullException();
        }
        return boardRepository.save(board);
    }

    @Override
    public Board update(Board board) {
        if (board.getId() == null) {
            throw new IdMustNotBeNullException();
        }
        return boardRepository.save(board);
    }

    @Override
    public Boolean deleteById(Long idBoard) {
        boardRepository.deleteById(idBoard);
        return boardRepository.findById(idBoard).isEmpty();
    }

    @Override
    public Board findById(Long idBoard) {
        return boardRepository.findById(idBoard).orElse(Board.builder().build());
    }

    @Override
    @SneakyThrows
    public Board getBoardFromTrello(String idBoard, String key, String token) {
        String url = "https://api.trello.com/1/boards/" + idBoard + "?key=" + key + "&token=" + token;
        URI targetURI = new URI(url);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetURI)
                .GET()
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        BoardTrelloDTO boardTrelloDTO = gson.fromJson(response.body(), BoardTrelloDTO.class);
        BoardDTO boardDTO = boardTrelloDTO.trellotoDto();
        Board board = boardDTO.toModel();
        return insert(board);
    }
}

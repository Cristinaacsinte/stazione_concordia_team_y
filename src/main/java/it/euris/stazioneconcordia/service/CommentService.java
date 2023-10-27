package it.euris.stazioneconcordia.service;

import it.euris.stazioneconcordia.data.model.Card;
import it.euris.stazioneconcordia.data.model.Comment;

import java.util.List;

public interface CommentService {


    List<Comment> findAll();

    Comment insert(Comment comment);

    Comment update(Comment comment);

    Boolean deleteById(String idComment);

    Comment findById(String idComment);

    Comment getLastComment(Card card);

    Comment[] getCommentsFromCard(String idCard, String key, String token);
}

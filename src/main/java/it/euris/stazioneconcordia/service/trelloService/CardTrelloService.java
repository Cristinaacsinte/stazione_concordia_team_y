package it.euris.stazioneconcordia.service.trelloService;

//import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.dto.CardDTO;
import it.euris.stazioneconcordia.data.trelloDto.CardTrelloDto;

import java.util.List;

public interface CardTrelloService {

    List<CardTrelloDto> getCardsByIdBoard(String idTrelloBoard);
    List<CardTrelloDto> getCardsByIdList(String idTrelloList);

    CardTrelloDto getCardByIdCard(String idCard);
    void update(String idCard, CardTrelloDto cardDTO);

    CardTrelloDto putCardToAnotherListByIdCardAndIdList(String idCard, String idList);
//     void update(String idCard, CardDTO cardDTO) throws URISyntaxException, IOException, InterruptedException;




}

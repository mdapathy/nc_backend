package net.dreamfteam.quiznet.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.dreamfteam.quiznet.data.dao.GameDao;
import net.dreamfteam.quiznet.data.dao.GameSessionDao;
import net.dreamfteam.quiznet.data.entities.ActivityType;
import net.dreamfteam.quiznet.data.entities.Game;
import net.dreamfteam.quiznet.data.entities.GameSession;
import net.dreamfteam.quiznet.exception.ValidationException;
import net.dreamfteam.quiznet.service.*;
import net.dreamfteam.quiznet.web.dto.DtoActivity;
import net.dreamfteam.quiznet.web.dto.DtoGameSession;
import net.dreamfteam.quiznet.web.dto.DtoGameWinner;
import net.dreamfteam.quiznet.web.dto.DtoPlayerSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class GameSessionServiceImpl implements GameSessionService {

    private final GameSessionDao gameSessionDao;
    private final GameService gameService;
    private final SseService sseService;
    private final ActivitiesService activitiesService;
    private final AchievementService achievementService;


    @Autowired
    public GameSessionServiceImpl(GameSessionDao gameSessionDao, SseService sseService,
                                  ActivitiesService activitiesService,
                                  GameService gameService,
                                  AchievementService achievementService) {
        this.gameSessionDao = gameSessionDao;
        this.sseService = sseService;
        this.activitiesService = activitiesService;
        this.gameService = gameService;
        this.achievementService = achievementService;
    }

    @Override
    public GameSession joinGame(String accessId, String userId, String username) {
        Game game = gameService.getGameByAccessId(accessId);
        if(game == null){
            throw new ValidationException("Game with access id: " + accessId + " doesn not exist");
        }

        GameSession gameSession = gameSessionDao.getSessionByAccessId(accessId, userId, username);

        String name = username;

        //IF SESSION CREATED FOR USER
        if (gameSession != null && Objects.equals(gameSession.getId(), userId)) {
            return gameSession;
        }
        //IF GAME CONTAINS PLAYER SESSION WITH SAME NAME
        else if (gameSession != null) {
            name = name + "(1)";
        }

        if (!gameSessionDao.gameHasAvailableSlots(accessId)) {
            throw new ValidationException("Sorry, no slots are available");
        }


        gameSession = GameSession.builder()
                .userId(userId.startsWith("-") ? null : userId)
                .username(name)
                .gameId(game.getId())
                .score(0)
                .winner(false)
                .creator(gameSessionDao.isCreator(game.getId()))
                .savedByUser(!userId.startsWith("-"))
                .durationTime(0)
                .build();

        gameSession = gameSessionDao.createSession(gameSession);

        sseService.send(game.getId(), "join", name);

        return gameSession;

    }

    @Override
    public void setResult(DtoGameSession dtoGameSession) {

        GameSession gameSession =
                GameSession.builder()
                        .score(dtoGameSession.getScore())
                        .winner(false)
                        .durationTime(dtoGameSession.getDurationTime())
                        .id(dtoGameSession.getSessionId())
                        .gameId(getGameIdBySessionId(dtoGameSession.getSessionId()))
                        .build();

        gameSessionDao.updateSession(gameSession);
        checkForGameOver(gameSession.getGameId());
    }

    @Override
    public List<DtoPlayerSession> getSessions(String gameId) {
        return gameSessionDao.getSessions(gameId);
    }

    @Override
    public void removePlayer(String sessionId) {
        String gameId = gameSessionDao.getGameId(sessionId);
        gameSessionDao.removePlayer(sessionId);
        checkForGameOver(gameId);
    }

    @Override
    public String getGameIdBySessionId(String sessionId) {
        return gameSessionDao.getGameId(sessionId);
    }

    @Override
    @Async
    public void timerForEnd(String gameId) {

        try {
            Thread.sleep((gameService.gameTime(gameId)+10)*1000);
            finshGame(gameId);
        } catch (InterruptedException e) {
            log.error("InterruptedException: "+e.getMessage());
        }
    }

    private void checkForGameOver(String gameId) {
        if (gameSessionDao.isGameFinished(gameId)) {
            finshGame(gameId);
        }
    }

    private void finshGame(String gameId){
        if (gameSessionDao.setWinnersForTheGame(gameId) > 0) {   //setting activities
            List<DtoGameWinner> winners = gameService.getWinnersOfTheGame(gameId);
            for (DtoGameWinner winner : winners) {
                DtoActivity activity = DtoActivity.builder()
                        .userId(winner.getUserId())
                        .activityType(ActivityType.GAMEPLAY_RELATED)
                        .content("Won the game while playing the quiz: \"" + winner.getQuizTitle() + "\"")
                        .contentUk("Виграв/ла гру граючи квіз: \"" + winner.getQuizTitle() + "\"")
                        .linkInfo(gameId)
                        .build();
                activitiesService.addActivityForUser(activity);
            }
        }

        //checking achievements
        List<DtoPlayerSession> sessionsMaps = getSessions(gameId);
        for (DtoPlayerSession session : sessionsMaps) {
            achievementService.checkAftergameAchievements(session.getGame_session_id());


            //sending message event to subscribers
            sseService.send(gameId, "finished", gameId);
            sseService.remove(gameId);
        }
    }


}

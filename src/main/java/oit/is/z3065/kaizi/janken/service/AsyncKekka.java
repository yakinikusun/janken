package oit.is.z3065.kaizi.janken.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3065.kaizi.janken.model.Match;
import oit.is.z3065.kaizi.janken.model.MatchInfoMapper;
import oit.is.z3065.kaizi.janken.model.MatchMapper;
import oit.is.z3065.kaizi.janken.model.UserMapper;

@Service
public class AsyncKekka {

    boolean MdbUpdated = false;
    boolean MIdbUpdated = false;
    private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

    @Autowired
    MatchMapper matchMapper;

    @Autowired
    MatchInfoMapper matchinfoMapper;

    @Autowired
    UserMapper userMapper;

    public void UpdateMatchDb() {
        this.MdbUpdated = true;
    }

    public void UpdateMatchInfoDb() {
        this.MIdbUpdated = true;
    }

    @Async
    public void asyncShowMatchinfo(SseEmitter emitter, int id) {
        try {
            while (true) {
                // アクティブな試合が見つかった場合
                if (matchMapper.selectActiveByUserId(id) == null) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    continue;
                }
                Match match = matchMapper.selectActiveByUserId(id);
                emitter.send(match);
                MdbUpdated = true;
                TimeUnit.MILLISECONDS.sleep(1000);
                matchMapper.updateMatchActive(match.getId(), false);
                break;
            }
        } catch (Exception e) {
            logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
        } finally {
            emitter.complete();
        }
    }

    @Async
    public void asyncShowMatch(SseEmitter emitter) {
        try {
            while (true) {
                if (!MdbUpdated) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    continue;
                }
                emitter.send(matchMapper.selectAllMatch());
                TimeUnit.MILLISECONDS.sleep(1000);
                MdbUpdated = false;
            }
        } catch (Exception e) {
            logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
        } finally {
            emitter.complete();
        }
    }
}

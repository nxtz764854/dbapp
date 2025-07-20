package service;

import dao.RelationsDAO;
import model.Relation;

import java.util.List;

public class RelationService {
    private RelationsDAO relationsDAO;

    public RelationService() {
        this.relationsDAO = new RelationsDAO();
    }

    public boolean addRelation(Relation relation) {
        return relationsDAO.insertRelation(relation);
    }

    // Overloaded method: auto-constructs Relation with default values
    public boolean addRelation(int playerID, int npcID) {
        Relation relation = new Relation(playerID, npcID, 0, 0, 0);
        return relationsDAO.insertRelation(relation);
    }

    public boolean createRelation(int playerID, int npcID) {
        Relation relation = new Relation(playerID, npcID, 0, -1, 0);
        return relationsDAO.insertRelation(relation);
    }


    public boolean updateRelation(Relation relation) {
        return relationsDAO.updateRelation(relation);
    }

    public boolean updateHearts(int playerID, int npcID, int newHearts) {
        return relationsDAO.updateHearts(playerID, npcID, newHearts);
    }

    public boolean updateGiftStats(int playerID, int npcID, int day, int count) {
        return relationsDAO.updateGiftStats(playerID, npcID, day, count);
    }

    public boolean incrementHearts(int playerID, int npcID, int amount) {
        return relationsDAO.incrementHearts(playerID, npcID, amount);
    }

    public Relation getRelation(int playerID, int npcID) {
        return relationsDAO.getRelation(playerID, npcID);
    }

    public List<Relation> getAllRelationsForPlayer(int playerID) {
        return relationsDAO.getRelationsByPlayer(playerID);
    }

    public boolean deleteRelation(int playerID, int npcID) {
        return relationsDAO.deleteRelation(playerID, npcID);
    }
}

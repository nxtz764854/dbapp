package service;

import dao.RelationsDAO;
import model.Relation;

import java.util.ArrayList;
import java.util.List;

public class RelationService {
    private RelationsDAO relationDAO;

    public RelationService() {
        this.relationDAO = new RelationsDAO();
    }

    public boolean createRelation(int playerID, int npcID) {
        Relation relation = new Relation();
        relation.setPlayerID(playerID);
        relation.setNpcID(npcID);
        relation.setNpchearts(0);
        return relationDAO.insertRelation(relation);
    }

    public boolean updateHearts(int playerID, int npcID, int newHeartValue) {
        return relationDAO.updateHearts(playerID, npcID, newHeartValue);
    }

    public Relation getRelation(int playerID, int npcID) {
        return relationDAO.getRelation(playerID, npcID);
    }

    public List<Relation> getRelationsByPlayer(int playerID) {
        List<Relation> relations = relationDAO.getRelationsByPlayer(playerID);
        return relations != null ? relations : new ArrayList<>();
    }

    public boolean deleteRelation(int playerID, int npcID) {
        return relationDAO.deleteRelation(playerID, npcID);
    }
}

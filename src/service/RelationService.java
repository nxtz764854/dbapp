package service;

import dao.RelationsDAO;
import model.Relation;

import java.util.List;

public class RelationService {
    private RelationsDAO relationDAO;

    public RelationService(RelationsDAO relationDAO) {
        this.relationDAO = relationDAO;
    }

    public boolean createRelation(int playerID, int npcID) {
        Relation relation = new Relation();
        relation.setPlayerID(playerID);
        relation.setNpcID(npcID);
        relation.setNpchearts(0);
        try {
            return relationDAO.insertRelation(relation);
        } catch (Exception e) {
            System.out.println("Failed to create relation: " + e.getMessage());
            return false;
        }
    }

    public boolean updateHearts(int playerID, int npcID, int newHeartValue) {
        try {
            return relationDAO.updateHearts(playerID, npcID, newHeartValue);
        } catch (Exception e) {
            System.out.println("Failed to update heart level: " + e.getMessage());
            return false;
        }
    }

    public Relation getRelation(int playerID, int npcID) {
        try {
            return relationDAO.getRelation(playerID, npcID);
        } catch (Exception e) {
            System.out.println("Failed to fetch relation: " + e.getMessage());
            return null;
        }
    }

    public List<Relation> getRelationsByPlayer(int playerID) {
        try {
            return relationDAO.getRelationsByPlayer(playerID);
        } catch (Exception e) {
            System.out.println("Failed to fetch relations: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteRelation(int playerID, int npcID) {
        try {
            return relationDAO.deleteRelation(playerID, npcID);
        } catch (Exception e) {
            System.out.println("Failed to delete relation: " + e.getMessage());
            return false;
        }
    }
}

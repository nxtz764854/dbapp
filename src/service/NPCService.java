package service;

import dao.NPCDAO;
import model.NPC;

import java.util.List;

public class NPCService {
    private NPCDAO npcDAO;

    public NPCService() {
        this.npcDAO = new NPCDAO();
    }

    public boolean addNPC(NPC npc) {
        return npcDAO.insertNPC(npc);
    }

    public boolean updateNPC(NPC npc) {
        return npcDAO.updateNPC(npc);
    }

    public boolean deleteNPC(int npcID) {
        return npcDAO.deleteNPC(npcID);
    }

    public NPC getNPCByID(int npcID) {
        return npcDAO.getNPCByID(npcID);
    }

    public NPC getNPCByName(String npcname) {
        return npcDAO.getNPCByName(npcname);
    }

    public List<NPC> getAllNPCs() {
        return npcDAO.getAllNPCs();
    }

    public boolean setGiftFlag(int npcID, boolean flag) {
        return npcDAO.setGiftFlag(npcID, flag);
    }

    public boolean resetAllGiftFlags() {
        return npcDAO.resetAllGiftFlags();
    }
}

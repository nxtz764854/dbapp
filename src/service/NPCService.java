package service;

import dao.NPCDAO;
import model.NPC;

import java.util.ArrayList;
import java.util.List;

public class NPCService {
    private NPCDAO npcDAO;

    public NPCService() {
        this.npcDAO = new NPCDAO();
    }

    public boolean createNPC(String npcname, boolean givinggifttoday) {
        NPC npc = new NPC();
        npc.setNpcname(npcname);
        npc.setGivinggifttoday(givinggifttoday);
        return npcDAO.insertNPC(npc);
    }

    public boolean updateNPC(int npcID, String newName, boolean giftFlag) {
        NPC npc = new NPC(npcID, newName, giftFlag);
        return npcDAO.updateNPC(npc);
    }

    public boolean deleteNPC(int npcID) {
        return npcDAO.deleteNPC(npcID);
    }

    public NPC getNPC(int npcID) {
        return npcDAO.getNPCByID(npcID);
    }

    public List<NPC> getAllNPCs() {
        List<NPC> npcs = npcDAO.getAllNPCs();
        return npcs != null ? npcs : new ArrayList<>();
    }

    public boolean setGiftFlag(int npcID, boolean flag) {
        return npcDAO.setGiftFlag(npcID, flag);
    }
}

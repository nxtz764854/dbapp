package service;

import dao.NPCDAO;
import model.NPC;

import java.util.List;

public class NPCService {
    private NPCDAO npcDAO;

    public NPCService(NPCDAO npcDAO) {
        this.npcDAO = npcDAO;
    }

    public boolean createNPC(String npcname, boolean givinggifttoday) {
        NPC npc = new NPC();
        npc.setNpcname(npcname);
        npc.setGivinggifttoday(givinggifttoday);
        try {
            return npcDAO.insertNPC(npc);
        } catch (Exception e) {
            System.out.println("Failed to create NPC: " + e.getMessage());
            return false;
        }
    }

    public boolean updateNPC(int npcID, String newName, boolean giftFlag) {
        NPC npc = new NPC(npcID, newName, giftFlag);
        try {
            return npcDAO.updateNPC(npc);
        } catch (Exception e) {
            System.out.println("Failed to update NPC: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteNPC(int npcID) {
        try {
            return npcDAO.deleteNPC(npcID);
        } catch (Exception e) {
            System.out.println("Failed to delete NPC: " + e.getMessage());
            return false;
        }
    }

    public NPC getNPC(int npcID) {
        try {
            return npcDAO.getNPCByID(npcID);
        } catch (Exception e) {
            System.out.println("Failed to retrieve NPC: " + e.getMessage());
            return null;
        }
    }

    public List<NPC> getAllNPCs() {
        try {
            return npcDAO.getAllNPCs();
        } catch (Exception e) {
            System.out.println("Failed to list NPCs: " + e.getMessage());
            return null;
        }
    }

    public boolean setGiftFlag(int npcID, boolean flag) {
        try {
            return npcDAO.setGiftFlag(npcID, flag);
        } catch (Exception e) {
            System.out.println("Failed to update gift flag: " + e.getMessage());
            return false;
        }
    }
}

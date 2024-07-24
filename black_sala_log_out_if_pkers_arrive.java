int lvl = client.getBoostedSkillLevel(Skill.HUNTER);
boolean logoutWhenLevelIsAchieved = true;
//CHECK GAME STATE TO SEE IF WE ARE LOGGED IN
if (client.getGameState().equals(GameState.LOGGED_IN)) {
        //LOOP THROUHG ALL THE PLAYERS AND SEE IF THEY ARE IN A DISTANCE OF 7 TILES
        //Its 7 tiles due to green dragon bots running a bit north and it can cause you to log out if you increase to >=15
    if (client.getPlayers().stream().anyMatch(player -> !player.equals(client.getLocalPlayer()) && player.getWorldLocation().distanceTo(client.getLocalPlayer().getWorldLocation()) < 8)) {
        //FOUND SOMEONE LOGGING OUT
        v.getGame().logout();
    }
}
if(logoutWhenLevelIsAchieved && lvl == 80) {
    if(client.getGameState().equals(GameState.LOGGED_IN)) {
        v.getGame().logout();
    }
} else {
    if (!v.getWalking().isMoving() && v.getLocalPlayer().hasAnimation(-1)) {
            v.getNpc().dismissRandomEvent();
            if(client.getEnergy() == 10000) {
                v.getWalking().turnRunningOn();
            }
        int avaliableTrees = v.getGameObject().count(9000);
        int setTrees = v.getGameObject().count(9002);
        int caughtTrees = v.getGameObject().count(8996);
        TileItem ropeItem = v.getGroundItem().findNearest(303);
        TileItem netItem = v.getGroundItem().findNearest(954);
        log.info(String.valueOf(setTrees + caughtTrees));
        int limit = 4;
        if (v.getInventory().amountInInventory(10148, 6, 28)) {
            ItemContainer itemContainer = client.getItemContainer(InventoryID.INVENTORY);
            if (itemContainer != null) {
                int index = 0;
                for (Item item: itemContainer.getItems()) {
                    if (item.getId() == 10148) {
                        v.invoke("Release","<col=ff9040>Black salamander</col>",7,1007,index,9764864,false);
                    }
                    index++;
                }
            }
        } else
        if (ropeItem != null && netItem != null) {
             v.getGroundItem().take(303, 954);
        } else if (ropeItem != null) {
            v.getGroundItem().take(303);
        } else if (netItem != null) {
            v.getGroundItem().take(954);
         } else if (v.getGameObject().findNearestByDistance(10, 9000) != null && (setTrees + caughtTrees) < limit) {
            v.getGameObject().invokeByDistance("Set-trap","<col=ffff>Young tree",9000,3, 10);
        } else if (caughtTrees >= 1) {
            v.getGameObject().invokeByDistance("Check", "<col=ffff>Net trap", 8996, 3, 10);
        }
    
    }
}
package nk0t.mods.usefulchest

import net.minecraft.item.ItemStack

object UsefulChestUtilities {

    def sortUsefulChest(usefulchest : TileEntityUsefulChest) = {

        val sortedItems = usefulchest.usefulChestContents.filter(_ != null)
            .sortWith((a, b) => {

                if (a.itemID == b.itemID) {
                    a.getItemDamage() < b.getItemDamage()
                }
                else {
                    a.itemID < b.itemID
                }
            })

        usefulchest.usefulChestContents = new Array[ItemStack](usefulchest.getSizeInventory())

        sortedItems.copyToArray(usefulchest.usefulChestContents)
    }
}

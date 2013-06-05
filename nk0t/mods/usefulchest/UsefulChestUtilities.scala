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

        val mergedItems = mergeStack(sortedItems)

        mergedItems.copyToArray(usefulchest.usefulChestContents)
    }

    def mergeStack(sortedItems : Array[ItemStack]) : Array[ItemStack] = {

        var mergedItems = List.empty[ItemStack]

        for (itemstack <- sortedItems) {

            if (0 < mergedItems.size && mergedItems.last.itemID == itemstack.itemID &&
                mergedItems.last.getItemDamage() == itemstack.getItemDamage()) {

                val size = mergedItems.last.getMaxStackSize() - mergedItems.last.stackSize
                if (size >= itemstack.stackSize) {
                    mergedItems.last.stackSize += itemstack.stackSize
                }
                else {
                    mergedItems.last.stackSize += size
                    itemstack.stackSize -= size
                    mergedItems = mergedItems :+ itemstack
                }
            }
            else {
                mergedItems = mergedItems :+ itemstack
            }
        }

        return mergedItems.toArray[ItemStack]
    }
}
